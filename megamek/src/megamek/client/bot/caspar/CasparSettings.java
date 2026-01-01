/*
 * Copyright (C) 2003 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2013-2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */
package megamek.client.bot.caspar;

import megamek.client.bot.princess.BehaviorSettings;
import megamek.client.bot.princess.PrincessException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * CASPAR behavior settings. Extends BehaviorSettings to allow CASPAR to work with
 * shared bot utilities while maintaining its own identity. CASPAR-specific settings
 * can be added here as the bot diverges from Princess.
 *
 * @author Deric "Netzilla" Page (deric dot page at usa dot net)
 * @since 17-Aug-2013 10:47 PM
 */
public class CasparSettings extends BehaviorSettings {

    public CasparSettings() {
        super();
    }

    public CasparSettings(final Element behavior) throws CasparException {
        super();
        try {
            fromXml(behavior);
        } catch (PrincessException e) {
            throw new CasparException(e);
        }
    }

    /**
     * Creates a copy of these settings.
     *
     * @return A new CasparSettings with the same values.
     */
    public CasparSettings getCasparCopy() throws CasparException {
        try {
            CasparSettings copy = new CasparSettings();
            copy.setDestinationEdge(getDestinationEdge());
            copy.setRetreatEdge(getRetreatEdge());
            copy.setForcedWithdrawal(isForcedWithdrawal());
            copy.setAutoFlee(shouldAutoFlee());
            copy.setDescription(getDescription());
            copy.setFallShameIndex(getFallShameIndex());
            copy.setBraveryIndex(getBraveryIndex());
            copy.setHerdMentalityIndex(getHerdMentalityIndex());
            copy.setHyperAggressionIndex(getHyperAggressionIndex());
            copy.setSelfPreservationIndex(getSelfPreservationIndex());
            copy.setAntiCrowding(getAntiCrowding());
            copy.setFavorHigherTMM(getFavorHigherTMM());
            copy.setNumberOfEnemiesToConsiderFacing(getNumberOfEnemiesToConsiderFacing());
            copy.setAllowFacingTolerance(getAllowFacingTolerance());
            copy.setExclusiveHerding(isExclusiveHerding());
            copy.setIAmAPirate(iAmAPirate());
            copy.setIgnoreDamageOutput(isIgnoreDamageOutput());
            copy.setExperimental(isExperimental());
            getStrategicBuildingTargets().forEach(copy::addStrategicTarget);
            getPriorityUnitTargets().forEach(copy::addPriorityUnit);
            getIgnoredUnitTargets().forEach(copy::addIgnoredUnitTarget);
            return copy;
        } catch (PrincessException e) {
            throw new CasparException(e);
        }
    }

    /**
     * @return TRUE if this is the default behavior settings for a CASPAR bot.
     */
    @Override
    public boolean isDefault() {
        return CasparSettingsFactory.DEFAULT_BEHAVIOR_DESCRIPTION.equalsIgnoreCase(getDescription());
    }

    /**
     * @return A string log of these behavior settings.
     */
    @Override
    public String toLog() {
        return super.toLog().replace("Princess Behavior:", "CASPAR Behavior:");
    }

    /**
     * Returns an XML representation of the behavior settings.
     * Package-visible override to allow CasparSettingsFactory access.
     *
     * @param doc The Document to create the Element in.
     * @param includeTargets Whether to include strategic targets.
     * @return An XML Element describing this behavior settings object.
     */
    @Override
    protected Element toXml(final Document doc, final boolean includeTargets) {
        return super.toXml(doc, includeTargets);
    }
}
