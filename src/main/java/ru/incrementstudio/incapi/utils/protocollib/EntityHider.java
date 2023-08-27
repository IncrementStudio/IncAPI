package ru.incrementstudio.incapi.utils.protocollib;

import static com.comphenix.protocol.PacketType.Play.Server.*;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class EntityHider implements Listener {
    protected Table<Integer, Integer, Boolean> observerEntityMap = HashBasedTable.create();

    private static final PacketType[] ENTITY_PACKETS = {
            ENTITY_EQUIPMENT, BED, ANIMATION, NAMED_ENTITY_SPAWN,
            COLLECT, SPAWN_ENTITY, SPAWN_ENTITY_LIVING, SPAWN_ENTITY_PAINTING, SPAWN_ENTITY_EXPERIENCE_ORB,
            ENTITY_VELOCITY, REL_ENTITY_MOVE, ENTITY_LOOK, ENTITY_MOVE_LOOK, ENTITY_MOVE_LOOK,
            ENTITY_TELEPORT, ENTITY_HEAD_ROTATION, ENTITY_STATUS, ATTACH_ENTITY, ENTITY_METADATA,
            ENTITY_EFFECT, REMOVE_ENTITY_EFFECT, BLOCK_BREAK_ANIMATION
    };

    public enum Policy {
        WHITELIST,
        BLACKLIST,
    }

    private static ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    private final Listener bukkitListener;
    private final PacketAdapter protocolListener;

    protected final Policy policy;
    public EntityHider(Plugin plugin, Policy policy) {
        Preconditions.checkNotNull(plugin, "Плагин не может равняться null");
        this.policy = policy;
        plugin.getServer().getPluginManager().registerEvents(
                bukkitListener = constructBukkit(), plugin);
        manager.addPacketListener(protocolListener = constructProtocol(plugin));
    }

    protected boolean setVisibility(Player observer, int entityID, boolean visible) {
        switch (policy) {
            case BLACKLIST:
                return !setMembership(observer, entityID, !visible);
            case WHITELIST:
                return setMembership(observer, entityID, visible);
            default :
                throw new IllegalArgumentException("Неизвестный тип: " + policy);
        }
    }

    protected boolean setMembership(Player observer, int entityID, boolean member) {
        if (member) {
            return observerEntityMap.put(observer.getEntityId(), entityID, true) != null;
        } else {
            return observerEntityMap.remove(observer.getEntityId(), entityID) != null;
        }
    }

    protected boolean getMembership(Player observer, int entityID) {
        return observerEntityMap.contains(observer.getEntityId(), entityID);
    }

    protected boolean isVisible(Player observer, int entityID) {
        boolean presence = getMembership(observer, entityID);

        return policy == Policy.WHITELIST ? presence : !presence;
    }
    protected void removeEntity(Entity entity, boolean destroyed) {
        int entityID = entity.getEntityId();

        for (Map<Integer, Boolean> maps : observerEntityMap.rowMap().values()) {
            maps.remove(entityID);
        }
    }

    protected void removePlayer(Player player) {
        observerEntityMap.rowMap().remove(player.getEntityId());
    }

    private Listener constructBukkit() {
        return new Listener() {
            @EventHandler
            public void onEntityDeath(EntityDeathEvent e) {
                removeEntity(e.getEntity(), true);
            }

            @EventHandler
            public void onChunkUnload(ChunkUnloadEvent e) {
                for (Entity entity : e.getChunk().getEntities()) {
                    removeEntity(entity, false);
                }
            }
            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent e) {
                removePlayer(e.getPlayer());
            }
        };
    }

    private PacketAdapter constructProtocol(Plugin plugin) {
        return new PacketAdapter(plugin, ENTITY_PACKETS) {
            @Override
            public void onPacketSending(PacketEvent event) {
                int entityID = event.getPacket().getIntegers().read(0);
                if (!isVisible(event.getPlayer(), entityID)) {
                    event.setCancelled(true);
                }
            }
        };
    }

    public final boolean toggleEntity(Player observer, Entity entity) {
        if (isVisible(observer, entity.getEntityId())) {
            return hideEntity(observer, entity);
        } else {
            return !showEntity(observer, entity);
        }
    }

    public final boolean showEntity(Player observer, Entity entity) {
        validate(observer, entity);
        boolean hiddenBefore = !setVisibility(observer, entity.getEntityId(), true);

        if (manager != null && hiddenBefore) {
            manager.updateEntity(entity, Arrays.asList(observer));
        }
        return hiddenBefore;
    }

    public final boolean hideEntity(Player observer, Entity entity) {
        validate(observer, entity);
        boolean visibleBefore = setVisibility(observer, entity.getEntityId(), false);

        if (visibleBefore) {
            PacketContainer destroyEntity = new PacketContainer(ENTITY_DESTROY);
            destroyEntity.getIntegerArrays().write(0, new int[] { entity.getEntityId() });

            manager.sendServerPacket(observer, destroyEntity);
        }
        return visibleBefore;
    }

    public final boolean canSee(Player observer, Entity entity) {
        validate(observer, entity);

        return isVisible(observer, entity.getEntityId());
    }

    private void validate(Player observer, Entity entity) {
        Preconditions.checkNotNull(observer, "Наблюдатель не может равнятся null.");
        Preconditions.checkNotNull(entity, "Сущность не может равнятся null.");
    }

    public Policy getPolicy() {
        return policy;
    }

    public void close() {
        if (manager != null) {
            HandlerList.unregisterAll(bukkitListener);
            manager.removePacketListener(protocolListener);
            manager = null;
        }
    }
}
