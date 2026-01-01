/*
 * Copyright (C) 2024-2025 The MegaMek Team. All Rights Reserved.
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
package megamek.common.planetaryConditions;

import megamek.common.Messages;

/**
 * Represents the toxicity level of a planetary atmosphere.
 * Based on Tactical Operations: Advanced Rules (TO:AR) p.56.
 *
 * <p>Atmosphere toxicity works in combination with {@link AtmosphereContamination}
 * to determine the full effects on units and personnel.</p>
 *
 * @see AtmosphereContamination
 * @see PlanetaryConditions
 */
public enum AtmosphereToxicity {
    BREATHABLE("TOXICITY_BREATHABLE",
            "PlanetaryConditions.DisplayableName.AtmosphereToxicity.Breathable",
            "\u2713"),
    TAINTED("TOXICITY_TAINTED",
            "PlanetaryConditions.DisplayableName.AtmosphereToxicity.Tainted",
            "\u26A0"),
    TOXIC("TOXICITY_TOXIC",
            "PlanetaryConditions.DisplayableName.AtmosphereToxicity.Toxic",
            "\u2620");

    private final String externalId;
    private final String name;
    private final String indicator;

    AtmosphereToxicity(final String externalId, final String name, final String indicator) {
        this.externalId = externalId;
        this.name = name;
        this.indicator = indicator;
    }

    public String getIndicator() {
        return indicator;
    }

    public String getExternalId() {
        return externalId;
    }

    @Override
    public String toString() {
        return Messages.getString(name);
    }

    public boolean isBreathable() {
        return this == BREATHABLE;
    }

    public boolean isTainted() {
        return this == TAINTED;
    }

    public boolean isToxic() {
        return this == TOXIC;
    }

    /**
     * Returns true if the atmosphere is hazardous (tainted or toxic).
     *
     * @return true if tainted or toxic
     */
    public boolean isHazardous() {
        return isTainted() || isToxic();
    }

    /**
     * Returns true if this toxicity level is more severe than the given level.
     *
     * @param other the toxicity level to compare against
     * @return true if this level is more severe
     */
    public boolean isMoreSevereThan(final AtmosphereToxicity other) {
        return compareTo(other) > 0;
    }

    public static AtmosphereToxicity getAtmosphereToxicity(int i) {
        return AtmosphereToxicity.values()[i];
    }

    public static AtmosphereToxicity getAtmosphereToxicity(String s) {
        for (AtmosphereToxicity condition : AtmosphereToxicity.values()) {
            if (condition.getExternalId().equals(s)) {
                return condition;
            }
        }
        return AtmosphereToxicity.BREATHABLE;
    }
}
