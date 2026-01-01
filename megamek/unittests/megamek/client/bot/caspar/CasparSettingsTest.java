/*
 * Copyright (c) 2000-2011 - Ben Mazur (bmazur@sev.org)
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import megamek.client.bot.princess.CardinalEdge;
import megamek.client.bot.princess.PrincessException;
import megamek.utilities.xml.MMXMLUtility;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Deric "Netzilla" Page (deric dot page at usa dot net)
 * @since 8/19/13 6:30 AM
 */
class CasparSettingsTest {

    @Test
    void testSetDescription() throws PrincessException {
        CasparSettings CasparSettings = new CasparSettings();

        // Test a normal description.
        String description = "Test behavior";
        CasparSettings.setDescription(description);
        assertTrue(true);

        // Test a null description.
        try {
            CasparSettings.setDescription(null);
            fail("Should have thrown an error!");
        } catch (PrincessException e) {
            assertTrue(true);
        }

        // Test an empty description.
        description = "";
        try {
            CasparSettings.setDescription(description);
            fail("Should have thrown an error!");
        } catch (PrincessException e) {
            assertTrue(true);
        }
    }

    @Test
    void testStrategicBuildingTargets() {
        CasparSettings CasparSettings = new CasparSettings();
        final String goodHexTarget = "1234";
        final String goodHexTarget2 = "4567";
        Set<String> expectedTargets = new HashSet<>(2);
        expectedTargets.add(goodHexTarget);

        // Test adding a normal hex target.
        CasparSettings.addStrategicTarget(goodHexTarget);
        Set<String> actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a duplicate target.
        CasparSettings.addStrategicTarget(goodHexTarget);
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a second target.
        expectedTargets.add(goodHexTarget2);
        CasparSettings.addStrategicTarget(goodHexTarget2);
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a null target.
        CasparSettings.addStrategicTarget(null);
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding an empty target.
        CasparSettings.addStrategicTarget("");
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a target.
        expectedTargets.remove(goodHexTarget2);
        CasparSettings.removeStrategicTarget(goodHexTarget2);
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a null target
        CasparSettings.removeStrategicTarget(null);
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing an empty target
        CasparSettings.removeStrategicTarget("");
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a target not on the list.
        CasparSettings.removeStrategicTarget("blah");
        actualTargets = CasparSettings.getStrategicBuildingTargets();
        assertEquals(expectedTargets, actualTargets);
    }

    @Test
    void testPreferredUnitTargets() {
        CasparSettings CasparSettings = new CasparSettings();
        final int goodUnitTarget = 1;
        final int goodUnitTarget2 = 4;
        Set<Integer> expectedTargets = new HashSet<>(2);
        expectedTargets.add(goodUnitTarget);

        // Test adding a normal hex target.
        CasparSettings.addPriorityUnit(goodUnitTarget);
        Set<Integer> actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a duplicate target.
        CasparSettings.addPriorityUnit(goodUnitTarget);
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a second target.
        expectedTargets.add(goodUnitTarget2);
        CasparSettings.addPriorityUnit(goodUnitTarget2);
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding a null target.
        CasparSettings.addPriorityUnit(null);
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test adding an empty target.
        CasparSettings.addPriorityUnit("");
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a target.
        expectedTargets.remove(goodUnitTarget2);
        CasparSettings.removePriorityUnit(goodUnitTarget2);
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a null target
        CasparSettings.removePriorityUnit(null);
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing an empty target
        CasparSettings.removePriorityUnit("");
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);

        // Test removing a target not on the list.
        CasparSettings.removePriorityUnit("blah");
        actualTargets = CasparSettings.getPriorityUnitTargets();
        assertEquals(expectedTargets, actualTargets);
    }

    @Test
    void testFromXml() throws ParserConfigurationException, IOException, SAXException, PrincessException {
        DocumentBuilder documentBuilder = MMXMLUtility.newSafeDocumentBuilder();

        // Test loading good behavior settings.
        Reader reader = new CharArrayReader(CasparSettingsTestConstants.GOOD_BEHAVIOR_XML.toCharArray());
        Document testDocument = documentBuilder.parse(new InputSource(reader));
        Element testBehaviorElement = testDocument.getDocumentElement();
        Set<String> expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        Set<Integer> expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading good behavior settings w/out any strategic targets.
        reader = new CharArrayReader(CasparSettingsTestConstants.GOOD_BEHAVIOR_XML_NO_TARGETS.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(0);
        expectedUnits = new HashSet<>(0);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ a NULL name.
        reader = new CharArrayReader(CasparSettingsTestConstants.BEHAVIOR_XML_NULL_NAME.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals("null", CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ an empty name.
        reader = new CharArrayReader(CasparSettingsTestConstants.BEHAVIOR_XML_EMPTY_NAME.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        try {
            CasparSettings.fromXml(testBehaviorElement);
            fail("Should have thrown an error!");
        } catch (PrincessException e) {
            assertTrue(true);
        }

        // Test loading behavior settings w/ a NULL home edge.
        reader = new CharArrayReader(CasparSettingsTestConstants.BEHAVIOR_XML_NULL_HOME_EDGE.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertSame(CardinalEdge.NONE, CasparSettings.getRetreatEdge());

        // Test loading behavior settings w/ a NULL forced withdrawal.
        reader = new CharArrayReader(
              CasparSettingsTestConstants.BEHAVIOR_XML_NULL_FORCED_WITHDRAWAL.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertFalse(CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ a NULL auto-flee.
        reader = new CharArrayReader(CasparSettingsTestConstants.BEHAVIOR_XML_NULL_AUTO_FLEE.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertFalse(CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ a Fall Shame > 10.
        // All other indexes use the same method for validation.
        reader = new CharArrayReader(
              CasparSettingsTestConstants.BEHAVIOR_XML_TOO_BIG_FALL_SHAME.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(10, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ a Fall Shame < 0.
        // All other indexes use the same method for validation.
        reader = new CharArrayReader(
              CasparSettingsTestConstants.BEHAVIOR_XML_TOO_SMALL_FALL_SHAME.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(2);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_1);
        expectedTargets.add(CasparSettingsTestConstants.STRATEGIC_TARGET_2);
        expectedUnits = new HashSet<>(1);
        expectedUnits.add(CasparSettingsTestConstants.PRIORITY_TARGET);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(0, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ a NULL strategic target.
        reader = new CharArrayReader(
              CasparSettingsTestConstants.BEHAVIOR_XML_NULL_STRATEGIC_TARGET.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(1);
        expectedTargets.add("null");
        expectedUnits = new HashSet<>(0);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());

        // Test loading behavior settings w/ an Empty strategic target.
        reader = new CharArrayReader(
              CasparSettingsTestConstants.BEHAVIOR_XML_EMPTY_STRATEGIC_TARGET.toCharArray());
        testDocument = documentBuilder.parse(new InputSource(reader));
        testBehaviorElement = testDocument.getDocumentElement();
        expectedTargets = new HashSet<>(0);
        expectedUnits = new HashSet<>(0);
        CasparSettings = new CasparSettings();
        CasparSettings.fromXml(testBehaviorElement);
        assertEquals(CasparSettingsTestConstants.GOOD_BEHAVIOR_NAME, CasparSettings.getDescription());
        assertEquals(CasparSettingsTestConstants.GOOD_HOME_EDGE, CasparSettings.getRetreatEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_DESTINATION_EDGE,
              CasparSettings.getDestinationEdge());
        assertEquals(CasparSettingsTestConstants.GOOD_FORCED_WITHDRAWAL,
              CasparSettings.isForcedWithdrawal());
        assertEquals(CasparSettingsTestConstants.GOOD_AUTO_FLEE, CasparSettings.shouldAutoFlee());
        assertEquals(CasparSettingsTestConstants.GOOD_FALL_SHAME_INDEX, CasparSettings.getFallShameIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HYPER_AGGRESSION_INDEX,
              CasparSettings.getHyperAggressionIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_SELF_PRESERVATION_INDEX,
              CasparSettings.getSelfPreservationIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_HERD_MENTALITY_INDEX,
              CasparSettings.getHerdMentalityIndex());
        assertEquals(CasparSettingsTestConstants.GOOD_BRAVERY_INDEX, CasparSettings.getBraveryIndex());
        assertEquals(expectedTargets, CasparSettings.getStrategicBuildingTargets());
        assertEquals(expectedUnits, CasparSettings.getPriorityUnitTargets());
    }
}
