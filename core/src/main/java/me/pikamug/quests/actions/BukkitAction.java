/*
 * Copyright (c) 2014 PikaMug and contributors. All rights reserved.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 * NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package me.pikamug.quests.actions;

import me.pikamug.quests.BukkitQuestsPlugin;
import me.pikamug.quests.entity.QuestMob;
import me.pikamug.quests.player.Quester;
import me.pikamug.quests.quests.Quest;
import me.pikamug.quests.tasks.BukkitActionTimer;
import me.pikamug.quests.util.BukkitConfigUtil;
import me.pikamug.quests.util.BukkitInventoryUtil;
import me.pikamug.quests.util.Language;
import me.pikamug.quests.util.BukkitMiscUtil;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BukkitAction implements Action {

    private final BukkitQuestsPlugin plugin;
    private String name = "";
    protected String message = null;
    protected boolean clearInv = false;
    protected boolean failQuest = false;
    protected LinkedList<Location> explosions = new LinkedList<>();
    protected Map<Location, Effect> effects = new HashMap<>();
    protected LinkedList<ItemStack> items = new LinkedList<>();
    protected World stormWorld = null;
    protected int stormDuration = 0;
    protected World thunderWorld = null;
    protected int thunderDuration = 0;
    protected int timer = 0;
    protected boolean cancelTimer = false;
    protected LinkedList<QuestMob> mobSpawns = new LinkedList<QuestMob>() {

        private static final long serialVersionUID = -761974607799449780L;

        @Override
        public boolean equals(final Object o) {
            if (o instanceof LinkedList) {
                @SuppressWarnings("unchecked")
                final
                LinkedList<QuestMob> other = (LinkedList<QuestMob>) o;
                if (size() != other.size()) {
                    return false;
                }
                for (int i = 0; i < size(); i++) {
                    if (!get(i).equals(other.get(i))) {
                        return false;
                    }
                }
            }
            return false;
        }
    };
    protected LinkedList<Location> lightningStrikes = new LinkedList<>();
    protected LinkedList<String> commands = new LinkedList<>();
    protected LinkedList<PotionEffect> potionEffects = new LinkedList<>();
    protected int hunger = -1;
    protected int saturation = -1;
    protected float health = -1;
    protected Location teleport;
    protected String book = "";
    protected String denizenScript;

    public BukkitAction(final BukkitQuestsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int compareTo(final Action action) {
        return name.compareTo(action.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public boolean isClearInv() {
        return clearInv;
    }

    @Override
    public void setClearInv(final boolean clearInv) {
        this.clearInv = clearInv;
    }

    @Override
    public boolean isFailQuest() {
        return failQuest;
    }

    @Override
    public void setFailQuest(final boolean failQuest) {
        this.failQuest = failQuest;
    }

    @Override
    public LinkedList<Location> getExplosions() {
        return explosions;
    }

    @Override
    public void setExplosions(final LinkedList<Location> explosions) {
        this.explosions = explosions;
    }

    @Override
    public Map<Location, Effect> getEffects() {
        return effects;
    }

    @Override
    public void setEffects(final Map<Location, Effect> effects) {
        this.effects = effects;
    }

    @Override
    public LinkedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setItems(final LinkedList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public World getStormWorld() {
        return stormWorld;
    }

    @Override
    public void setStormWorld(final World stormWorld) {
        this.stormWorld = stormWorld;
    }

    @Override
    public int getStormDuration() {
        return stormDuration;
    }

    @Override
    public void setStormDuration(final int stormDuration) {
        this.stormDuration = stormDuration;
    }

    @Override
    public World getThunderWorld() {
        return thunderWorld;
    }

    @Override
    public void setThunderWorld(final World thunderWorld) {
        this.thunderWorld = thunderWorld;
    }

    @Override
    public int getThunderDuration() {
        return thunderDuration;
    }

    @Override
    public void setThunderDuration(final int thunderDuration) {
        this.thunderDuration = thunderDuration;
    }

    @Override
    public int getTimer() {
        return timer;
    }

    @Override
    public void setTimer(final int timer) {
        this.timer = timer;
    }

    @Override
    public boolean isCancelTimer() {
        return cancelTimer;
    }

    @Override
    public void setCancelTimer(final boolean cancelTimer) {
        this.cancelTimer = cancelTimer;
    }

    @Override
    public LinkedList<QuestMob> getMobSpawns() {
        return mobSpawns;
    }

    @Override
    public void setMobSpawns(final LinkedList<QuestMob> mobSpawns) {
        this.mobSpawns = mobSpawns;
    }

    @Override
    public LinkedList<Location> getLightningStrikes() {
        return lightningStrikes;
    }

    @Override
    public void setLightningStrikes(final LinkedList<Location> lightningStrikes) {
        this.lightningStrikes = lightningStrikes;
    }

    @Override
    public LinkedList<String> getCommands() {
        return commands;
    }

    @Override
    public void setCommands(final LinkedList<String> commands) {
        this.commands = commands;
    }

    @Override
    public LinkedList<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    @Override
    public void setPotionEffects(final LinkedList<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public void setHunger(final int hunger) {
        this.hunger = hunger;
    }

    @Override
    public int getSaturation() {
        return saturation;
    }

    @Override
    public void setSaturation(final int saturation) {
        this.saturation = saturation;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(final float health) {
        this.health = health;
    }

    @Override
    public Location getTeleport() {
        return teleport;
    }

    @Override
    public void setTeleport(final Location teleport) {
        this.teleport = teleport;
    }

    @Override
    public String getBook() {
        return book;
    }

    @Override
    public void setBook(final String book) {
        this.book = book;
    }

    @Override
    public String getDenizenScript() {
        return book;
    }

    @Override
    public void setDenizenScript(final String scriptName) {
        this.denizenScript = scriptName;
    }

    public void fire(final Quester quester, final Quest quest) {
        final Player player = quester.getPlayer();
        if (message != null) {
            player.sendMessage(BukkitConfigUtil.parseStringWithPossibleLineBreaks(message, quest, player));
        }
        if (clearInv) {
            player.getInventory().clear();
        }
        if (!explosions.isEmpty()) {
            for (final Location l : explosions) {
                if (l.getWorld() != null) {
                    l.getWorld().createExplosion(l, 4F, false);
                }
            }
        }
        if (!effects.isEmpty()) {
            for (final Location l : effects.keySet()) {
                if (l.getWorld() != null) {
                    l.getWorld().playEffect(l, effects.get(l), 1);
                }
            }
        }
        if (!items.isEmpty()) {
            for (final ItemStack is : items) {
                try {
                    BukkitInventoryUtil.addItem(player, is);
                } catch (final Exception e) {
                    plugin.getLogger().severe("Unable to add null item to inventory of " 
                            + player.getName() + " during quest " + quest.getName() + " event " + name);
                    player.sendMessage(ChatColor.RED + "Quests encountered a problem with an item. "
                            + "Please contact an administrator.");
                }
            }
        }
        if (stormWorld != null) {
            stormWorld.setStorm(true);
            stormWorld.setWeatherDuration(stormDuration);
        }
        if (thunderWorld != null) {
            thunderWorld.setThundering(true);
            thunderWorld.setThunderDuration(thunderDuration);
        }
        if (!mobSpawns.isEmpty()) {
            for (final QuestMob questMob : mobSpawns) {
                questMob.spawn();
            }
        }
        if (!lightningStrikes.isEmpty()) {
            for (final Location l : lightningStrikes) {
                if (l.getWorld() != null) {
                    l.getWorld().strikeLightning(l);
                }
            }
        }
        if (!commands.isEmpty()) {
            for (final String s : commands) {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
                        s.replace("<player>", quester.getPlayer().getName()));
            }
        }
        if (!potionEffects.isEmpty()) {
            for (final PotionEffect p : potionEffects) {
                player.addPotionEffect(p);
            }
        }
        if (hunger != -1) {
            player.setFoodLevel(hunger);
        }
        if (saturation != -1) {
            player.setSaturation(saturation);
        }
        if (health != -1) {
            player.setHealth(health);
        }
        if (teleport != null) {
            if (player.isDead()) {
                plugin.getLogger().warning("Tried to fire Action " + name + " but player " + player.getUniqueId()
                + " was dead (known Bukkit limitation).");
            } else {
                player.teleport(teleport);
            }
        }
        if (book != null) {
            if (!book.isEmpty()) {
                if (plugin.getDependencies().getAstralBooksApi() != null) {
                    if (plugin.getDependencies().getAstralBooksApi().hasFilterBook(book)) {
                        plugin.getDependencies().getAstralBooksApi().openBook(player, plugin.getDependencies()
                                .getAstralBooksApi().getFilterBook(book));
                    }
                }
            }
        }
        if (failQuest) {
            quest.failQuest(quester, true);
        }
        if (timer > 0) {
            player.sendMessage(ChatColor.GREEN + Language.get(player, "timerStart")
                    .replace("<time>", ChatColor.RED + BukkitMiscUtil.getTime(timer * 1000L) + ChatColor.GREEN)
                    .replace("<quest>", ChatColor.GOLD + quest.getName() + ChatColor.GREEN));
            final List<Integer> toNotify = Arrays.asList(60, 30, 10, 5, 4, 3, 2, 1);
            for (final int seconds : toNotify) {
                if (timer > seconds) {
                    quester.getTimers().put(new BukkitActionTimer(quester, quest, seconds)
                            .runTaskLater(plugin, (timer - seconds) * 20L).getTaskId(), quest);
                }
            }
            quester.getTimers().put(new BukkitActionTimer(quester, quest, 0)
                    .runTaskLater(plugin, timer * 20L).getTaskId(), quest);
        }
        if (cancelTimer) {
            for (final Map.Entry<Integer, Quest> entry : quester.getTimers().entrySet()) {
                if (entry.getValue().getId().equals(quest.getId())) {
                    plugin.getServer().getScheduler().cancelTask(entry.getKey());
                    quester.getTimers().remove(entry.getKey());
                }
            }
        }
        if (denizenScript != null) {
            plugin.getDenizenTrigger().runDenizenScript(denizenScript, quester, null);
        }
    }
}
    
