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

package me.pikamug.quests.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class BukkitActionBarProvider {

    private static BukkitActionBarProvider loaded;

    static {
        final String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            final String packageName = BukkitActionBarProvider.class.getPackage().getName();
            if (internalsName.startsWith("v1_8_R")) {
                loaded = (BukkitActionBarProvider) Class.forName(packageName + ".BukkitActionBarProvider_" + internalsName)
                        .newInstance();
            } else {
                // Should not be an issue because single thread, alternatives welcome!
                loaded = new BukkitActionBarProvider_Modern();
            }
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
                | ClassCastException exception) {
            Bukkit.getLogger().severe("[Quests] No valid action bar implementation for version " + internalsName);
        }
    }

    abstract void sendActionBarPacket(Player player, String message);

    /**
     * Sends the action bar to the player.
     *
     * @param player
     *                   The player to send the action bar to.
     * @param message
     *                   The message,
     */
    public static void sendActionBar(final Player player, final String message) {
        loaded.sendActionBarPacket(player, message);
    }
}
