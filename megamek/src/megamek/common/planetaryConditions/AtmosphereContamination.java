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
 * Represents the type of contamination in a planetary atmosphere.
 * Based on Tactical Operations: Advanced Rules (TO:AR) p.56.
 *
 * <p>Atmosphere contamination works in combination with {@link AtmosphereToxicity}
 * to determine the full effects on units and personnel:</p>
 * <ul>
 *   <li><b>NONE</b> - No special contamination effects</li>
 *   <li><b>CAUSTIC</b> - Corrosive atmosphere damaging to exposed units and personnel</li>
 *   <li><b>RADIOLOGICAL</b> - Radioactive contamination with cumulative exposure effects</li>
 *   <li><b>FLAMMABLE</b> - Combustible atmosphere with increased fire risks</li>
 * </ul>
 *
 * @see AtmosphereToxicity
 * @see PlanetaryConditions
 */
public enum AtmosphereContamination {
    NONE("CONTAMINATION_NONE",
            "PlanetaryConditions.DisplayableName.AtmosphereContamination.None",
            "\u2205"),
    CAUSTIC("CONTAMINATION_CAUSTIC",
            "PlanetaryConditions.DisplayableName.AtmosphereContamination.Caustic",
            "\u2623"),
    RADIOLOGICAL("CONTAMINATION_RADIOLOGICAL",
            "PlanetaryConditions.DisplayableName.AtmosphereContamination.Radiological",
            "\u2622"),
    FLAMMABLE("CONTAMINATION_FLAMMABLE",
            "PlanetaryConditions.DisplayableName.AtmosphereContamination.Flammable",
            "\u2668");

    private final String externalId;
    private final String name;
    private final String indicator;

    AtmosphereContamination(final String externalId, final String name, final String indicator) {
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

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isCaustic() {
        return this == CAUSTIC;
    }

    public boolean isRadiological() {
        return this == RADIOLOGICAL;
    }

    public boolean isFlammable() {
        return this == FLAMMABLE;
    }

    /**
     * Returns true if the atmosphere has any type of contamination.
     *
     * @return true if not NONE
     */
    public boolean isContaminated() {
        return !isNone();
    }

    public static AtmosphereContamination getAtmosphereContamination(int i) {
        return AtmosphereContamination.values()[i];
    }

    public static AtmosphereContamination getAtmosphereContamination(String s) {
        for (AtmosphereContamination condition : AtmosphereContamination.values()) {
            if (condition.getExternalId().equals(s)) {
                return condition;
            }
        }
        return AtmosphereContamination.NONE;
    }
}
