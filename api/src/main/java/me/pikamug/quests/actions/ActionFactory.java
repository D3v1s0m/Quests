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

import org.bukkit.block.Block;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ActionFactory {
    Map<UUID, Block> getSelectedExplosionLocations();

    void setSelectedExplosionLocations(final Map<UUID, Block> selectedExplosionLocations);

    Map<UUID, Block> getSelectedEffectLocations();

    void setSelectedEffectLocations(final Map<UUID, Block> selectedEffectLocations);

    Map<UUID, Block> getSelectedMobLocations();

    void setSelectedMobLocations(final Map<UUID, Block> selectedMobLocations);

    Map<UUID, Block> getSelectedLightningLocations();

    void setSelectedLightningLocations(final Map<UUID, Block> selectedLightningLocations);

    Map<UUID, Block> getSelectedTeleportLocations();

    void setSelectedTeleportLocations(final Map<UUID, Block> selectedTeleportLocations);

    ConversationFactory getConversationFactory();

    List<String> getNamesOfActionsBeingEdited();

    void setNamesOfActionsBeingEdited(final List<String> actionNames);

    Prompt returnToMenu(final ConversationContext context);

    void loadData(final Action event, final ConversationContext context);

    void clearData(final ConversationContext context);

    void deleteAction(final ConversationContext context);

    void saveAction(final ConversationContext context);
}
