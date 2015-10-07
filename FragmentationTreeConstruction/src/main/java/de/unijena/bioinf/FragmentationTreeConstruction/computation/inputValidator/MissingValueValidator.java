/*
 *  This file is part of the SIRIUS library for analyzing MS and MS/MS data
 *
 *  Copyright (C) 2013-2015 Kai Dührkop
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with SIRIUS.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.unijena.bioinf.FragmentationTreeConstruction.computation.inputValidator;

import de.unijena.bioinf.ChemistryBase.chem.PeriodicTable;
import de.unijena.bioinf.ChemistryBase.chem.PrecursorIonType;
import de.unijena.bioinf.ChemistryBase.ms.*;
import de.unijena.bioinf.ChemistryBase.ms.utils.SimpleSpectrum;
import de.unijena.bioinf.ChemistryBase.ms.utils.Spectrums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: kaidu
 * Date: 19.04.13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */
public class MissingValueValidator implements InputValidator {


    private boolean validDouble(double val, boolean mayNegative) {
        return !Double.isInfinite(val) && !Double.isNaN(val) && (mayNegative || val > 0d);
    }

    @Override
    public MutableMs2Experiment validate(Ms2Experiment originalInput, Warning warn, boolean repair) throws InvalidException {
        final MutableMs2Experiment input = new MutableMs2Experiment(originalInput);
        if (input.getMs2Spectra() == null || input.getMs2Spectra().isEmpty())
            throw new InvalidException("Miss MS2 spectra");
        if (input.getMs1Spectra() == null) input.setMs1Spectra(new ArrayList<SimpleSpectrum>());
        removeEmptySpectra(warn, input);
        checkIonization(warn, repair, input);
        checkMergedMs1(warn, repair, input);
        checkIonMass(warn, repair, input);
        checkNeutralMass(warn, repair, input);
        return input;
    }

    protected void checkNeutralMass(Warning warn, boolean repair, MutableMs2Experiment input) {
        if (input.getMoleculeNeutralMass() == 0 || !validDouble(input.getMoleculeNeutralMass(), false)) {
            throwOrWarn(warn, repair, "Neutral mass is missing");
            if (input.getMolecularFormula() != null) {
                input.setMoleculeNeutralMass(input.getMolecularFormula().getMass());
            } else if (input.getPrecursorIonType() != null) {
                input.setMoleculeNeutralMass(input.getPrecursorIonType().precursorMassToNeutralMass(input.getIonMass()));
            }
        }
    }

    protected void removeEmptySpectra(Warning warn, MutableMs2Experiment input) {
        final Iterator<MutableMs2Spectrum> iter = input.getMs2Spectra().iterator();
        while (iter.hasNext()) {
            final Ms2Spectrum spec = iter.next();
            if (spec.size() == 0) {
                warn.warn("Empty Spectrum at collision energy: " + spec.getCollisionEnergy());
                iter.remove();
            }
        }
    }

    protected void checkIonization(Warning warn, boolean repair, MutableMs2Experiment input) {
        if ((input.getMolecularFormula()!=null || input.getMoleculeNeutralMass()>0) && input.getIonMass()>0 && input.getPrecursorIonType()!=null) {
            final double neutralmass;
            if (input.getMolecularFormula()!=null) neutralmass=input.getMolecularFormula().getMass();
            else neutralmass = input.getMoleculeNeutralMass();
            final double modification = input.getIonMass()-neutralmass;
            if (Math.abs(input.getPrecursorIonType().neutralMassToPrecursorMass(neutralmass)-input.getIonMass()) > 1e-2) {
                final PrecursorIonType iontype = PeriodicTable.getInstance().ionByMass(modification, 1e-2, input.getPrecursorIonType().getCharge());
                if (iontype != null) {
                    throwOrWarn(warn, true, "PrecursorIonType is inconsistent with the data.");
                    input.setPrecursorIonType(iontype);
                } else {
                    throwOrWarn(warn, true, "PrecursorIonType is inconsistent with the data.");
                    input.setPrecursorIonType(PeriodicTable.getInstance().getUnknownPrecursorIonType(input.getPrecursorIonType().getCharge()));
                }
            }
        }
        if (input.getPrecursorIonType() == null) {
            throwOrWarn(warn, repair, "No ionization is given");
            if (validDouble(input.getIonMass(), false) && validDouble(input.getMoleculeNeutralMass(), false)) {
                double modificationMass = input.getIonMass() - input.getMoleculeNeutralMass();
                PrecursorIonType ion = PeriodicTable.getInstance().ionByMass(modificationMass, 1e-2);
                if (ion == null && input.getMolecularFormula() != null) {
                    modificationMass = input.getIonMass() - input.getMolecularFormula().getMass();
                    ion = PeriodicTable.getInstance().ionByMass(modificationMass, 1e-2);
                }
                if (ion == null) {
                    searchForIon(warn, input);
                } else {
                    warn.warn("set ion to " + ion);
                    input.setPrecursorIonType(ion);
                }
            } else if (input.getMolecularFormula() != null || validDouble(input.getMoleculeNeutralMass(), false)) {
                searchForIon(warn, input);
            } else {
                throwOrWarn(warn, repair, "Use protonation.");
                input.setPrecursorIonType(PeriodicTable.getInstance().getUnknownPrecursorIonType(1));
            }
        }
        if (repair && input.getPrecursorIonType().isIonizationUnknown() && (input.getMolecularFormula() != null)) {
            double modificationMass = input.getIonMass() - (input.getMolecularFormula() != null ? input.getMolecularFormula().getMass() : input.getMoleculeNeutralMass());
            PrecursorIonType ion = PeriodicTable.getInstance().ionByMass(modificationMass, 1e-2, input.getPrecursorIonType().getCharge());
            if (ion != null) {
                warn.warn("Set ion to " + ion.toString());
                input.setPrecursorIonType(ion);
            } else {
                searchForIon(warn, input);
            }
        }
    }

    private void searchForIon(Warning warn, MutableMs2Experiment input) {
        final double neutral = (input.getMolecularFormula() != null) ? input.getMolecularFormula().getMass() : input.getMoleculeNeutralMass();
        final ArrayList<Spectrum<? extends Peak>> spectra = new ArrayList<Spectrum<? extends Peak>>(input.getMs1Spectra());
        for (Ms2Spectrum<? extends Peak> ms2 : input.getMs2Spectra()) spectra.add(ms2);
        // TODO: negative ions
        // search for [M+H]+
        final PrecursorIonType mhp = PeriodicTable.getInstance().ionByName("[M+H]+");
        final double mz = mhp.neutralMassToPrecursorMass(neutral);
        final Deviation dev = new Deviation(20);
        for (Spectrum<? extends Peak> spec : spectra) {
            final int peak = Spectrums.search(spec, mz, dev);
            if (peak >= 0) {
                warn.warn("Set ion to " + mhp.toString());
                input.setPrecursorIonType(mhp);
                input.setIonMass(spec.getMzAt(peak));
                return;
            }
        }
        // search for other ions
        final Collection<PrecursorIonType> ions = PeriodicTable.getInstance().getIons();
        for (Spectrum<? extends Peak> spec : spectra) {
            for (PrecursorIonType ion : ions) {
                if (Spectrums.search(spec, ion.neutralMassToPrecursorMass(neutral), dev) >= 0) {
                    warn.warn("Set ion to " + ion.toString());
                    input.setPrecursorIonType(ion);
                    return;
                }
            }
        }
        throw new InvalidException("Unknown ionization");
    }

    protected void checkMergedMs1(Warning warn, boolean repair, MutableMs2Experiment input) {
        if (input.getMergedMs1Spectrum() == null && !input.getMs1Spectra().isEmpty()) {
            warn.warn("No merged spectrum is given");
            if (repair) {
                if (input.getMs1Spectra().size() == 1)
                    input.setMergedMs1Spectrum(input.getMs1Spectra().get(0));
            }
        }
    }

    protected void checkIonMass(Warning warn, boolean repair, MutableMs2Experiment input) {
        if (!validDouble(input.getIonMass(), false) || input.getIonMass() == 0) {
            throwOrWarn(warn, repair, "No ion mass is given");
            if (input.getMolecularFormula() == null && !validDouble(input.getMoleculeNeutralMass(), false)) {
                final Spectrum<Peak> ms1 = input.getMergedMs1Spectrum();
                // maybe the ms2 spectra have a common precursor
                boolean found = true;
                double mz = input.getMs2Spectra().get(0).getPrecursorMz();
                for (Ms2Spectrum s : input.getMs2Spectra()) {
                    if (!validDouble(s.getPrecursorMz(), false) || s.getPrecursorMz() == 0) {
                        found = false;
                        break;
                    }
                    final double newMz = s.getPrecursorMz();
                    if (Math.abs(mz - newMz) > 1e-3) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    input.setIonMass(mz);
                } else {
                    if (ms1 == null) {
                        // use the highest mass you find in the MS2 spectra with lowest collision energy as parent mass.
                        // (only if its intensity is higher than 10% and higher than peaks in its neighbourhood)
                        Ms2Spectrum spec = input.getMs2Spectra().get(0);
                        for (Ms2Spectrum spec2 : input.getMs2Spectra()) {
                            if (spec2.getCollisionEnergy().lowerThan(spec.getCollisionEnergy())) spec = spec2;
                        }
                        final Spectrum<Peak> normalized = Spectrums.getNormalizedSpectrum(spec, Normalization.Max(1d));
                        Peak parent = normalized.getPeakAt(Spectrums.getIndexOfPeakWithMaximalIntensity(normalized));
                        for (Peak p : normalized)
                            if (p.getIntensity() > 0.1d) {
                                if (Math.abs(p.getMass() - parent.getMass()) < 1e-2) {
                                    if (p.getIntensity() > parent.getIntensity()) parent = p;
                                } else if (p.getMass() > parent.getMass()) parent = p;
                            }
                        input.setIonMass(parent.getMass());
                    } else {
                        // take peak with highest intensity
                        int index = Spectrums.getIndexOfPeakWithMaximalIntensity(ms1);
                        // move backward, maybe you are in the middle of an isotope pattern
                        while (index > 0) {
                            if (Math.abs(ms1.getMzAt(index) - ms1.getMzAt(index - 1)) > 1.1d) break;
                            --index;
                        }
                        // hopefully, this is the correct isotope peak
                        warn.warn("Predict ion mass from MS1: " + ms1.getMzAt(index));
                        input.setIonMass(ms1.getMzAt(index));
                    }
                }
            } else {
                final double neutralMass = (input.getMolecularFormula()!=null ? input.getMolecularFormula().getMass() : input.getMoleculeNeutralMass());
                if (neutralMass <= 0) {
                    throwOrWarn(warn, false, "Neither ionmass nor neutral mass nor molecular formula are given. Cannot determine parent peak!");
                }
                final double parentMz = input.getPrecursorIonType().neutralMassToPrecursorMass(neutralMass);
                input.setIonMass(parentMz);
                for (int i = 0; i < input.getMs2Spectra().size(); ++i) {
                    final Ms2Spectrum s = input.getMs2Spectra().get(i);
                    if (Math.abs(s.getPrecursorMz() - parentMz) > 0.1d) {
                        final MutableMs2Spectrum t = new MutableMs2Spectrum(s, parentMz, s.getCollisionEnergy(), 2);
                        input.getMs2Spectra().set(i, t);
                    }
                }
            }
        }
    }

    protected void throwOrWarn(Warning warn, boolean repair, String message) {
        if (repair) warn.warn(message);
        else throw new InvalidException(message);
    }
}
