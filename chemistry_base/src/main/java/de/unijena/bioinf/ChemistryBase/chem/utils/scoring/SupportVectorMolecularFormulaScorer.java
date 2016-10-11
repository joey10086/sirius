package de.unijena.bioinf.ChemistryBase.chem.utils.scoring;

import de.unijena.bioinf.ChemistryBase.algorithm.HasParameters;
import de.unijena.bioinf.ChemistryBase.chem.MolecularFormula;
import de.unijena.bioinf.ChemistryBase.chem.utils.MolecularFormulaScorer;

import java.util.HashSet;

@HasParameters
public class SupportVectorMolecularFormulaScorer implements MolecularFormulaScorer {

    private final static HashSet<MolecularFormula> WHITESET = new HashSet<>();
    private final static String[] whiteset = new String[]{
            "C42H42N14",
            "CH3N5O3",
            "C41H25N9O2",
            "CH3N5O2",
            "C10H20S4",
            "C2H8N6O2",
            "C5H16N8",
            "C6H8N6O18",
            "C12H18Br6",
            "C28H14",
            "C6H6N12O12",
            "C24H44Cl6",
            "C6H16O16P4",
            "C14H24N2O2S8",
            "C40H82O21",
            "H6O12P4",
            "C24H28Cl4N2S4",
            "C16H30N10O2S3",
            "C17H17F17O4",
            "H6O18P6",
            "C34H76N8",
            "C16H6N6",
            "C6H12O18S4",
            "C20H46N8",
            "C63H35BrO3",
            "H2O14",
            "C5H14O22P6",
            "C15H25Cl5O4S",
            "C14H17F11O3",
            "C8H14N4S8",
            "C39H60F17NO7",
            "C24H57N7",
            "C4H8N6S2",
            "C23H52N8",
            "C3H15N6PS",
            "C60H60N40O20",
            "C8H12Br4",
            "H4O6P2S",
            "C27H38F17N2O4P",
            "C52H37N5",
            "C38H65F9N2O8",
            "C18H29F7O2",
            "C19H22F17NO",
            "C14H28Cl4N4O6P2",
            "O6P4",
            "C11H22Cl4N4O",
            "C6H20N2O12P4",
            "C27H41F13NO4",
            "O10P4",
            "C10H20O8S6",
            "C13H19N3O3S12",
            "CH5O8P3",
            "C26H12N4",
            "C10H2N10O2",
            "C74H46",
            "H5NO6P2",
            "CH5NO2S6",
            "C16H25Cl6NO4",
            "H2O6P2",
            "H2O8S2",
            "C32H14",
            "C41H30Cl2N10O",
            "CH4N4O4",
            "C22H48N4S4",
            "H2O20",
            "C18H26F11NO2",
            "C10H17N7O12S2",
            "CH7O10P3",
            "C22H39Cl5O8S2",
            "C12H3Cl4N5O10",
            "C10H32N12P4",
            "C3H12NO9P3",
            "C63H40N4",
            "C10H6N10",
            "H4O8P2",
            "C6H10S6",
            "H6O13P4",
            "C22H48N10S4",
            "C8H16N10O6",
            "C31H62O21S10",
            "C27H62N8",
            "C14H11F17O4",
            "C13H4F21N",
            "C2H6N4S4",
            "C10H18O21S4",
            "C23H54N8",
            "C10H20N8OP2S",
            "C44H30Cl2N4",
            "C28H50Cl4N8",
            "C12H18BrF6NO",
            "C6H14ClN3O5S2",
            "C3H11N3O7P2",
            "C54H18",
            "C25H15N5",
            "C28H47F9NO4",
            "C12H7F17O5",
            "C20H13N9",
            "C12H20N2S6",
            "C43H41N15",
            "C10H2N4",
            "C42H18",
            "C24H48N9P3",
            "C14H2F26",
            "C14H14F14N2O2",
            "C4H18N8",
            "C12H14Cl4N8",
            "C66H56S8",
            "C80H22",
            "P4S10",
            "C2H6N6",
            "C15H33N7S3",
            "C11H26N10",
            "C5H12N6O4S3",
            "C18H20F17N2O4P",
            "C35H78N8",
            "C3H6N6O4",
            "C6H19O24P7",
            "C18H38Cl4N10O4P6",
            "C3H8N6O3",
            "C6H15N4O9",
            "C40H67Cl11O11S",
            "C17H15F17O2",
            "H2O6S4",
            "C4H18N3O15P5",
            "C17H20N8S4",
            "C6H13F2O14P3",
            "C14H17F13",
            "C14H18F11NO2",
            "C20H42Cl4N10O4P6",
            "C2HN9",
            "C13H3ClN4O",
            "C12H13F13N2O3",
            "CH7N3O6P2",
            "C16H9F26O4P",
            "C42H69F13N2O8",
            "C38H58F18NO8P",
            "C10H16Cl6",
            "C10H16Cl4N2S2",
            "C60H38N12",
            "C36H12",
            "H2O13",
            "C38H16",
            "H2O9",
            "C18H30Br6O2",
            "C18H32F6N2O",
            "C40H92N10",
            "C35H23N7",
            "C15H10F18N4",
            "C2H6N4O4S",
            "C22H40Cl6O2",
            "C14H28N2O2S4",
            "C14H28F4N9O2P3",
            "C18H34BrIO2",
            "C6H2N20",
            "C42H42N28O14",
            "C12H21Br3O",
            "C2H10N4O6",
            "C17H29F6NO",
            "CH7O9P3",
            "C3H6N6O6",
            "C7H12N11O11",
            "C48H24",
            "C5H13N7S3",
            "C6H21N9O6",
            "C66H20",
            "C6H6F8INO3S2",
            "C9H20O18",
            "C6H17O21P5",
            "C13H6F18N4",
            "C16H32N9P3",
            "C6H6N10",
            "C41H42N14",
            "C6H12N2O4S5",
            "C14H11F17O2",
            "C8H20N10",
            "C10H6F17NO2S",
            "H6NO9P3",
            "C31H70N8",
            "C15H13F17O2",
            "C22H35F9NO4",
            "C24H12N4",
            "C2H2N8O",
            "C10HBr4F7N2",
            "C42H39N15",
            "C20H36N6O10S8",
            "C26H12N4S3",
            "C8H20N2O6S4",
            "C10H17N9O2S3",
            "C6N12O6",
            "C3H12O12P4",
            "C50H36P2",
            "C11H6ClF16NO2",
            "C10H14N11O13P3",
            "C12H28O2P4",
            "C15H24Cl6O4S",
            "C22H42Cl4O8S2",
            "C18H31F6NO",
            "C12Cl2F6N8S2",
            "C68H53N8",
            "C32H10F28N4",
            "C3H8O17",
            "C26H58N8",
            "C6H12S5",
            "C17H7N11O16",
            "C15H23Cl6N3O3",
            "H2O6S2",
            "C13H24BrI",
            "C17H17F17O2",
            "C3H15NO18P6",
            "C4H4N8O14",
            "C25H41F7O5",
            "C20H40O19S4",
            "C6H14N4O6S2",
            "C60H122O31",
            "C25H38F13N2O4P",
            "C7H11Br2IO",
            "C22H40Cl6O8S2",
            "C36H74O19",
            "C18H36N2S4",
            "C15H11F17O2",
            "C16H20F13N2O4P",
            "C22H34N9P3",
            "C40H20",
            "C16H30Br2N2O2S2",
            "C3H9O12P3",
            "C9H28N3O15P5",
            "C6H24N2O24P8",
            "C24H56N8",
            "C14H10F17NO3",
            "C60H46P4",
            "C15H6N4",
            "C5H14NO12P3",
            "C19HF15",
            "C41H96N28O8",
            "C16H26BrF6NO",
            "C38H87N9",
            "H4O5P2S2",
            "C6H19NO24P6",
            "C14H4N6",
            "C28H39F17NO4",
            "C55H112O28",
            "C21H32N16",
            "C30H68N8",
            "C16H30N18",
            "C48H32",
            "C26H52N2O20",
            "C16H15N17O2",
            "C12N6",
            "C17H18F17NO",
            "C8H14N7O2S3",
            "C10H24N10",
            "C15H23F9N2O",
            "C16H28F6N2O",
            "C4H13N3O7P2",
            "C16H32N12P4",
            "C5H2N8O2",
            "C14H28N9OP3",
            "C15H14F17NO",
            "C18H39N15",
            "H4O5P2",
            "C24H51N15",
            "C2H9NO12P4",
            "C36H18",
            "C13H10F17NO",
            "N3P3",
            "C9H18S5",
            "C5H14N10",
            "C12H32N4O12P4",
            "C29H13F46NO10S",
            "C15H11F17O4",
            "C3H9O13P3",
            "C14H8F18N4",
            "C32H18N2",
            "C44H40N14",
            "C6H10N8S2",
            "C18H38N14",
            "C9H21N7S3",
            "C15H12F17NO",
            "C32H74N8",
            "C5H8N4O12",
            "C4H6N8S2",
            "C10H20N2S8",
            "C13H9F17O2",
            "C18F15P",
            "C4H6N4O11",
            "C40H30N8",
            "C10H18Cl2N6O4S2",
            "N2O5",
            "C9H20Cl2N6O2",
            "C4H10N8",
            "H2O7P2",
            "H4NO6P2",
            "C16H34N14",
            "C32H64N12P4",
            "C6H15O12P3S3",
            "C18H32Br4O2",
            "C40H90N10",
            "CH4Br2O6P2",
            "C16H32N2O4S6",
            "C6N4",
            "C19H24F12N4O2",
            "H4O6P2",
            "C32H72N8",
            "C5H15N4O6PS",
            "C28H18N8",
            "C9H18O3S6",
            "C22H16N10",
            "CH8N3O9P3",
            "C6H10N4O12S2",
            "C10H5F17S",
            "C12H22N4S4",
            "C3H6N6O3S",
            "C14H22N9P3",
            "C2H6N6S",
            "C2H8N6",
            "C56H84F26N2O9",
            "C6H13NO13S2",
            "C9H27N3O18P6",
            "C18H40N10S4",
            "C99H78N21O",
            "C44H32P2",
            "C44H34N8",
            "C14H22ClF6NO",
            "C16H15F17O2",
            "C15H25Cl5O",
            "C52H36N6",
            "C4H14N4O6S2",
            "C15H34N9O2P3",
            "C2H12N2O12P4",
            "C24H42Cl6O8S2",
            "C18H44N4O12P4",
            "C15H14F17NO3",
            "C13H9F17O4",
            "C5H6N6S4",
            "C43H19Cl6N3O6",
            "C16H21F13",
            "C5H11NS5",
            "C3H6S6",
            "C10H16Br4",
            "CN8O8",
            "C16H15F17O4",
            "C11H24N9P3",
            "C32H18N8",
            "CH5N5O2",
            "H4O7P2",
            "H5O9P3S",
            "C7H12N2S7",
            "C15H3Cl4NO5",
            "C8H9N11",
            "C48H48N32O16",
            "C48H26N8",
            "C41H58F22N2O8",
            "C13H9ClF16O2",
            "C6H16NO14P3",
            "C48H108N12",
            "H2S6",
            "C13H19F9N2O",
            "C3H6N6O5",
            "C40H24",
            "C10H20N2S4",
            "C3H14N4O3P2",
            "C6H16O18P4",
            "C18H42N4S3",
            "C7H13I2N",
            "C13H10F17NO3",
            "C36H12F8N8O8",
            "C44H38N16",
            "C15H31BrN2S2",
            "C6HCl4N7",
            "C11HBr4F9N2",
            "C11H9ClF12O2",
            "C38H36N14",
            "C17H30F6N2O",
            "C4H8N8O8",
            "H7N2O8P3",
            "C15H22F9NO",
            "C32H16",
            "C8H8F8INO4S",
            "C24H14N6",
            "C6H16N4O9P2",
            "C24H42Cl6O5S",
            "C12H26N9P3",
            "C10H18N4S4",
            "C4H4N6O14",
            "C7H22N2O13P4",
            "C17H18F17NO3",
            "C2H2N8",
            "H2O8",
            "CN4O8",
            "C16H26ClF6NO",
            "CH5BrO6P2",
            "H3O9P",
            "C44H36N8",
            "C14H26N14",
            "C20H9F34O4P",
            "C16H14F17NO",
            "C40H26N8",
            "C13H2Cl4F11NO",
            "C11H7F17O",
            "C13H9F17O3",
            "C39H38N14",
            "C33H80N12O8",
            "C28H50F4N8",
            "C168H213N91O49",
            "C5H10S7",
            "C6H12O6S6",
            "C12H15NS7",
            "C42H16",
            "C44H10F20N4",
            "C15H13F17O4",
            "C12H24S6",
            "C22H40Cl6O5S",
            "C16H28Cl4N2O2",
            "C16H10F18N4",
            "C28H22N12",
            "C14H34Cl2N6",
            "C18H44N8",
            "C3H11O12P3",
            "C12H22O23S4",
            "C11H10ClN11",
            "C15H24Cl6O",
            "CH6O8P2",
            "C12H20N2O2S8",
            "C6H13NO14S3",
            "H2O7S2",
            "H2S7",
            "C6H3N7",
            "C12H11F17NO6PS",
            "C2H9O10P3",
            "C10H24N6O4S4",
            "C4H4N14",
            "C22H46Cl2N2",
            "C34H70F2N2",
            "C4H8S8",
            "C12H22O20S3",
            "C6H12N6O8",
            "C62H40N12O4S4",
            "C6H16N6S2",
            "C17H27F9N2O",
            "C16H4F6N8S2",
            "C12H8F17NO3",
            "H4O6P2S2",
            "C14H36N4O12P4",
            "C11H32N6P2S2",
            "C6H10S7",
            "C3H5N7",
            "C40H67Cl11O10S",
            "C40H16",
            "C12H8F17NO",
            "C5H14NO13P3",
            "C10H8F4N10O18",
            "C34H18",
            "C12HCl8NS",
            "C11H25N13",
            "C40H40N14",
            "C6H18O24P6",
            "C10H22O14S4",
            "C2H9O8P3",
            "C15H12F17NO3",
            "C13H9F17O5",
            "C14H26N4S4",
            "P4S3",
            "C35H57F10NO9",
            "C40H28N8",
            "C22H52N8",
            "C4H6N4O12",
            "C2H5N5O4",
            "C2H12N6",
            "C8H15N7O2S3",
            "C44H30N4",
            "C17H26F9NO",
            "C10H24N2S4",
            "C7H14S5",
            "C5H10S6",
            "CH4N6",
            "C32H24N12",
            "C9H16Br4O",
            "C5H6N8O13",
            "H3O9P3",
            "C3H10N6S2",
            "C32H18",
            "C10H25N9",
            "C14H22BrF6NO",
            "C8H22N2O14P4",
            "C2H8N3OP3",
            "C14H10F17NO",
            "C27H65N9O6",
            "C14H14N4S6",
            "C8H12N2O2S8",
            "C12H9F17O2",
            "C12H24N9P3",
            "C12H10F17NO3S",
            "C18H20N6S8",
            "C56H128N14",
            "C17H16F17NO3",
            "C96H24",
            "C18H23F13",
            "C2H4S6",
            "C2H8N4O2S2",
            "C12H27N7S3",
            "C16H14F17NO3",
            "C18H37N3S3",
            "C30H18N6",
            "H5O10P3",
            "CH8N6",
            "C12HCl9",
            "C12H7ClF16O",
            "C5H9F3N8",
            "C18H40N4S2",
            "C10H20S5",
            "C18H19F17",
            "C12H19Cl3O11",
            "C12H7F17O",
            "C12H24N6P2S2",
            "C26H39F13N2O7",
            "C54H110O28",
            "C22H50N8",
            "C12H8F17NO4S",
            "C18H21N11O38",
            "C24H12N6",
            "C47HF95O15",
            "C10H8S8",
            "C12H27N15",
            "C17H36N14",
            "C14H32N10S4",
            "CH7N2O11P3",
            "C40H22N10",
            "C16H30N4S4",
            "C6H12O24S6",
            "C26H60N8",
            "C6H14O6S6",
            "C44H24N2",
            "C38H22",
            "C18H30Cl4F8N10O4P6",
            "C8H24N9P3",
            "C22H43Cl3O8S2",
            "C9H3N13O2S",
            "C10H4S8",
            "C3H2N6O14",
            "CH7N2O10P3",
            "C10H25N15",
            "C9H14N8OS3",
            "H2O6S3",
            "C6H12O15S3",
            "C12H33N3O18P6",
            "C22H42O2S4",
            "C5H12N8",
            "C28H50N4S4",
            "C15H3Cl6N9O3",
            "C16H22F11NO2",

    };
    private final static double[] CENTERING_VECTOR = new double[]{
            8.626983189958759, 0.05084538751847313, 512.2404362782152, 6.153154946368798, 0.15176513949575196, 2.071731281521563, 1.5722330637476818, 1.6058452590167192, 0.4658860114389061, 2.2037372173675096, 0.8134178937097339, -3.741673400474228, 0.3243163443050614, 0.847017507493676, -1.4732801719496997, -1.1614596025882917, -1.1023222136128104, -1.2194187657197768, 0.10371526027990084, -3.5661839196462277, -1.3889572273202866, -3.196727380179048, -2.0016374436217066, 29.836597768160665, 20.1756769410529, 5.47262719489307, 4.865271326035004, 0.5292414921812342, 0.8711298025054249, 0.4004567901737187, 0.10648666473962554, 0.03026947363278467, 0.7714176373118591, 3.1595907428143004, 2.800163249831959, 1.4360435495322499, 1.3135735083178646, 0.17619987850553687, 0.3466424047842486, 0.18294642933438035, 0.059575717504553524, 0.018980388621015035, 0.250493863545421, 0.4317735838629477, 0.9219774442286918, 0.13428565794015815, 0.11882409260553616, 0.036959471359290154, 0.012344484240950176, 0.003188897831446235, 0.07431825865676911, 3.282298498092882, 0.6210946808972542, 1.9313186253954167, 2.4145879728205775, 0.3997565414225635, 0.020559629031010433, 0.03157226200702692, 0.41007947721514953, 0.8799796192497314
    };
    private final static double[] NORMALIZATION_VECTOR = new double[]{
            76.37301681004124, 0.9491546124815269, 987.6307388297848, 2.2408189886353336, 0.6528315906823815, 99.17826871847844, 99.67776693625231, 78.3941535488904, 100.78411247981855, 727.796251904785, 119.18658210629026, 29.77485275003967, 29.84840915469521, 11.534626675268719, 14.189502795801578, 15.96373142024624, 14.724350314402916, 14.06791014454166, 13.172819788064427, 360.03236639250156, 40.08162301563446, 100.1757510635737, 19.435655971195686, 150.16340223183934, 84.8243230589471, 62.52737280510693, 56.13472867396499, 30.470758507818765, 28.128870197494575, 21.59954320982628, 10.893513335260375, 6.969730526367215, 45.228582362688144, 3.1595907428143004, 2.800163249831959, 2.79806295506501, 2.813560876727227, 3.28953602429419, 3.054554976877907, 2.9525477865947694, 2.4253309322834466, 2.060461153058821, 3.5996537381646374, 75.81822641613705, 79.07802255577131, 34.86571434205984, 38.631175907394464, 13.71304052864071, 11.23765551575905, 4.996811102168554, 32.42568174134323, 81.71770150190711, 79.37890531910274, 433.0686813746046, 457.5854120271794, 223.60024345857744, 29.97944037096899, 33.968427737992975, 0.5899205222363428, 0.8794883505201259
    };
    private final static double[] SUPPORT_VECTORS = new double[]{
            7.614347071953642,
            0.2263579409382770,
            -1.159331213759453,
            -6.304518439193006,
            -5.344727424757036,
            0.3151600726116055,
            5.069045760370413,
            -0.3342277340080716,
            0.5701148654606777,
            3.094327028743607,
            -4.518246858777677,
            8.221305587199929,
            -2.450345734840525,
            2.916819485070509,
            -0.4519031725216033,
            1.619980688625354,
            0.8849136356689550,
            1.519840065490680,
            -3.030294206087451,
            -2.920016384749638,
            4.339977489380953,
            7.339820318240517,
            2.705169525460252,
            -1.923877780969624,
            7.359403225993809,
            0.7958026195824400,
            0.3432958369138751,
            -14.68184530862326,
            -6.459487476125909,
            1.000560351500448,
            3.752178856685967,
            5.975500620497439,
            -0.1725120131973438,
            -3.448712681471990,
            4.473519784101486,
            0.4785093463461297,
            1.067902268282465,
            3.585366044689709,
            1.436568170198121,
            -0.6970885735891111,
            -0.7671561156039262,
            -1.825653010955734,
            -0.5238968593247937,
            -4.988865640546100,
            4.334259565842354,
            1.711063837714120,
            1.098880140236541,
            3.917410764195476,
            1.402454083243475,
            3.521946428715927,
            -2.022125345550465,
            -0.5967203360606901,
            2.467236226772499,
            10.02331428526009,
            5.928481562126894,
            -2.102820619151080,
            3.050789023696670,
            2.285425632040188,
            0.2024267472252549,
            0.1350639073747924
    };


    public SupportVectorMolecularFormulaScorer() {
    }

    public static boolean inWhiteset(MolecularFormula f) {
        if (WHITESET.isEmpty()) {
            for (String s : whiteset) WHITESET.add(MolecularFormula.parse(s));
        }
        return WHITESET.contains(f);
    }

    public static double getDecisionValue(MolecularFormula formula) {
        final double[] vector = new FormulaFeatureVector(formula).getLogFeatures();
        FormulaFeatureVector.normalizeAndCenter(new double[][]{vector}, CENTERING_VECTOR, NORMALIZATION_VECTOR);
        double decisionValue = 0d;
        for (int k = 0; k < SUPPORT_VECTORS.length; ++k) {
            decisionValue += SUPPORT_VECTORS[k] * vector[k];
        }
        return decisionValue;
    }

    public static double getLogScore(MolecularFormula f) {
        if (inWhiteset(f)) return 0d;
        else return 2 * Math.max(-5, Math.min(0, getDecisionValue(f)));
    }

    @Override
    public double score(MolecularFormula formula) {
        return getLogScore(formula);
    }
}
