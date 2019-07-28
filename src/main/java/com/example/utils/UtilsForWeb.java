package com.example.utils;

import com.example.models.Car;
import com.example.models.Coord;
import com.example.models.Segment;

import java.util.ArrayList;
import java.util.logging.Logger;

public class UtilsForWeb {

    public String algorithmMorning(Integer countOfCars) {
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < countOfCars; i++) {
            cars.add(new Car(30, new Coord(52.2797298616311, 104.34527349498241), i));
        }
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Lenina", new Coord(52.28576568355886, 104.28068161010744),
                new Coord(52.28209020513799, 104.28089618682863), 10, 10.5, 3, 0));
        segments.add(new Segment("Lenina", new Coord(52.28209020513799, 104.28089618682863),
                new Coord(52.27599874106406, 104.28626060485841), 10, 10.5, 2, 1));
        segments.add(new Segment("Sovetskya", new Coord(52.27634797179878, 104.30354261385219),
                new Coord(52.27991890346087, 104.32371282564418), 10, 10.5, 1, 2));
        segments.add(new Segment("Sovetskya", new Coord(52.27991890346087, 104.32371282564418),
                new Coord(52.2797298616311, 104.34527349498241), 10, 10.5, 1, 3));
        segments.add(new Segment("Sovetskya", new Coord(52.2797298616311, 104.34527349498241),
                new Coord(52.2806094257078, 104.34774112727611), 10, 10.5, 1, 4));
//        segments.add(new Segment("Lenina", new Coord(52.285766, 104.280682), new Coord(52.285766, 104.280682), 10, 10.5, 1, 5));
//        segments.add(new Segment("Lenina", new Coord(52.282090, 104.280896), new Coord(52.282090, 104.280896), 10, 10.5,2, 6));
        segments.add(new Segment("Marksa", new Coord(52.290984, 104.295882), new Coord(52.290984, 104.295882), 10, 10.5,2, 7));
        segments.add(new Segment("Sovetskya", new Coord(52.276348, 104.303543), new Coord(52.276348, 104.303543), 10, 10.5,2, 8));
//        segments.add(new Segment("Sovetskya", new Coord(52.279919, 104.323713), new Coord(52.279919, 104.323713), 10, 10.5,2, 9));
//        segments.add(new Segment("Sovetskya", new Coord(52.279730, 104.345273), new Coord(52.279730, 104.345273), 10, 10.5,2, 10));
//        segments.add(new Segment("Sovetskya", new Coord(52.280609, 104.347741), new Coord(52.280609, 104.347741), 10, 10.5,2, 11));
//        segments.add(new Segment("Sovetskya", new Coord(52.280670, 104.355374), new Coord(52.280670, 104.355374), 10, 10.5,2, 12));
//        segments.add(new Segment("Baikalskaya", new Coord(52.276353, 104.303500), new Coord(52.276353, 104.303500), 10, 10.5,2, 13));
//        segments.add(new Segment("Baikalskaya", new Coord(52.275447, 104.303972), new Coord(52.275447, 104.303972), 10, 10.5,2, 14));
//        segments.add(new Segment("Baikalskaya", new Coord(52.274212, 104.304777), new Coord(52.274212, 104.304777), 10, 10.5,2, 15));
//        segments.add(new Segment("Baikalskaya", new Coord(52.272355, 104.305882), new Coord(52.272355, 104.305882), 10, 10.5,2, 16));
//        segments.add(new Segment("Baikalskaya", new Coord(52.268967, 104.307920), new Coord(52.268967, 104.307920), 10, 10.5,2, 17));
//        segments.add(new Segment("Baikalskaya", new Coord(52.268520, 104.308199), new Coord(52.268520, 104.308199), 10, 10.5,2, 18));
//        segments.add(new Segment("Baikalskaya", new Coord(52.264876, 104.310442), new Coord(52.264876, 104.310442), 10, 10.5,2, 19));
//        segments.add(new Segment("Baikalskaya", new Coord(52.264180, 104.310935), new Coord(52.264180, 104.310935), 10, 10.5,2, 20));
//        segments.add(new Segment("Baikalskaya", new Coord(52.263622, 104.312083), new Coord(52.263622, 104.312083), 10, 10.5,2, 21));
//        segments.add(new Segment("Baikalskaya", new Coord(52.258205, 104.326846), new Coord(52.258205, 104.326846), 10, 10.5,2, 22));
//        segments.add(new Segment("Baikalskaya", new Coord(52.257889, 104.327747), new Coord(52.257889, 104.327747), 10, 10.5,2, 23));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253404, 104.340568), new Coord(52.253404, 104.340568), 10, 10.5,2, 24));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253069, 104.340815), new Coord(52.253069, 104.340815), 10, 10.5,2, 25));
//        segments.add(new Segment("Koljo Platina", new Coord(52.252573, 104.341979), new Coord(52.252573, 104.341979), 10, 10.5,2, 26));
//        segments.add(new Segment("Koljo Platina", new Coord(52.252402, 104.342666), new Coord(52.252402, 104.342666), 10, 10.5,2, 27));
//        segments.add(new Segment("Koljo Platina", new Coord(52.252415, 104.343390), new Coord(52.252415, 104.343390), 10, 10.5,2, 28));
//        segments.add(new Segment("Koljo Platina", new Coord(52.252763, 104.343766), new Coord(52.252763, 104.343766), 10, 10.5,2, 29));
//        segments.add(new Segment("Koljo Platina", new Coord(52.252990, 104.343835), new Coord(52.252990, 104.343835), 10, 10.5,2, 30));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253371, 104.343546), new Coord(52.253371, 104.343546), 10, 10.5,2, 31));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253725, 104.342939), new Coord(52.253725, 104.342939), 10, 10.5,2, 32));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253847, 104.342339), new Coord(52.253847, 104.342339), 10, 10.5,2, 33));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253762, 104.341416), new Coord(52.253762, 104.341416), 10, 10.5,2, 34));
//        segments.add(new Segment("Koljo Platina", new Coord(52.253801, 104.341432), new Coord(52.253801, 104.341432), 10, 10.5,2, 35));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.253758, 104.342907), new Coord(52.253758, 104.342907), 10, 10.5,2, 36));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.254166, 104.343143), new Coord(52.254166, 104.343143), 10, 10.5,2, 37));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.256743, 104.346303), new Coord(52.256743, 104.346303), 10, 10.5,2, 38));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.257410, 104.347188), new Coord(52.257410, 104.347188), 10, 10.5,2, 39));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.259626, 104.349978), new Coord(52.259626, 104.349978), 10, 10.5,2, 40));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.260316, 104.351099), new Coord(52.260316, 104.351099), 10, 10.5,2, 41));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.260789, 104.352778), new Coord(52.260789, 104.352778), 10, 10.5,2, 42));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.261012, 104.353234), new Coord(52.261012, 104.353234), 10, 10.5,2, 43));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.261399, 104.353272), new Coord(52.261399, 104.353272), 10, 10.5,2, 44));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.262148, 104.352156), new Coord(52.262148, 104.352156), 10, 10.5,2, 45));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.263149, 104.350804), new Coord(52.263149, 104.350804), 10, 10.5,2, 46));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.264092, 104.349527), new Coord(52.264092, 104.349527), 10, 10.5,2, 47));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.265648, 104.347451), new Coord(52.265648, 104.347451), 10, 10.5,2, 48));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.266472, 104.346931), new Coord(52.266472, 104.346931), 10, 10.5,2, 49));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.267247, 104.346673), new Coord(52.267247, 104.346673), 10, 10.5,2, 50));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.267713, 104.346904), new Coord(52.267713, 104.346904), 10, 10.5,2, 51));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.268179, 104.347558), new Coord(52.268179, 104.347558), 10, 10.5,2, 52));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.268625, 104.348267), new Coord(52.268625, 104.348267), 10, 10.5,2, 53));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.269062, 104.349050), new Coord(52.269062, 104.349050), 10, 10.5,2, 54));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.269499, 104.349661), new Coord(52.269499, 104.349661), 10, 10.5,2, 55));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.269827, 104.349897), new Coord(52.269827, 104.349897), 10, 10.5,2, 56));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.270510, 104.350241), new Coord(52.270510, 104.350241), 10, 10.5,2, 57));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.271294, 104.350326), new Coord(52.271294, 104.350326), 10, 10.5,2, 58));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.272023, 104.350337), new Coord(52.272023, 104.350337), 10, 10.5,2, 59));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.272663, 104.350294), new Coord(52.272663, 104.350294), 10, 10.5,2, 60));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.272952, 104.350444), new Coord(52.272952, 104.350444), 10, 10.5,2, 61));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.273149, 104.350825), new Coord(52.273149, 104.350825), 10, 10.5,2, 62));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.273254, 104.351796), new Coord(52.273254, 104.351796), 10, 10.5,2, 63));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.273431, 104.354393), new Coord(52.273431, 104.354393), 10, 10.5,2, 64));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.273937, 104.355267), new Coord(52.273937, 104.355267), 10, 10.5,2, 65));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.274065, 104.356544), new Coord(52.274065, 104.356544), 10, 10.5,2, 66));
//        segments.add(new Segment("Shiryamova_Koljo-Airport", new Coord(52.274147, 104.356640), new Coord(52.274147, 104.356640), 10, 10.5,2, 67));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.275480, 104.356510), new Coord(52.275480, 104.356510), 10, 10.5,2, 68));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.275380, 104.354930), new Coord(52.275380, 104.354930), 10, 10.5,2, 69));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.275300, 104.354760), new Coord(52.275300, 104.354760), 10, 10.5,2, 70));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.275150, 104.354690), new Coord(52.275150, 104.354690), 10, 10.5,2, 71));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.274270, 104.354610), new Coord(52.274270, 104.354610), 10, 10.5,2, 72));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.274200, 104.354610), new Coord(52.274200, 104.354610), 10, 10.5,2, 73));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273730, 104.354570), new Coord(52.273730, 104.354570), 10, 10.5,2, 74));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273570, 104.354500), new Coord(52.273570, 104.354500), 10, 10.5,2, 75));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273500, 104.354370), new Coord(52.273500, 104.354370), 10, 10.5,2, 76));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273420, 104.352430), new Coord(52.273420, 104.352430), 10, 10.5,2, 77));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273370, 104.351740), new Coord(52.273370, 104.351740), 10, 10.5,2, 78));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273290, 104.350820), new Coord(52.273290, 104.350820), 10, 10.5,2, 79));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.273060, 104.350260), new Coord(52.273060, 104.350260), 10, 10.5,2, 80));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.272850, 104.350120), new Coord(52.272850, 104.350120), 10, 10.5,2, 81));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.272040, 104.350170), new Coord(52.272040, 104.350170), 10, 10.5,2, 82));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.271230, 104.350180), new Coord(52.271230, 104.350180), 10, 10.5,2, 83));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.270910, 104.350140), new Coord(52.270910, 104.350140), 10, 10.5,2, 84));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.270620, 104.350070), new Coord(52.270620, 104.350070), 10, 10.5,2, 85));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.270430, 104.350000), new Coord(52.270430, 104.350000), 10, 10.5,2, 86));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.270180, 104.349850), new Coord(52.270180, 104.349850), 10, 10.5,2, 87));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.269730, 104.349550), new Coord(52.269730, 104.349550), 10, 10.5,2, 88));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.269440, 104.349280), new Coord(52.269440, 104.349280), 10, 10.5,2, 89));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.269220, 104.348950), new Coord(52.269220, 104.348950), 10, 10.5,2, 90));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.268480, 104.347760), new Coord(52.268480, 104.347760), 10, 10.5,2, 91));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267930, 104.346850), new Coord(52.267930, 104.346850), 10, 10.5,2, 92));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267800, 104.346680), new Coord(52.267800, 104.346680), 10, 10.5,2, 93));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267660, 104.346580), new Coord(52.267660, 104.346580), 10, 10.5,2, 94));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267560, 104.346530), new Coord(52.267560, 104.346530), 10, 10.5,2, 95));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267400, 104.346490), new Coord(52.267400, 104.346490), 10, 10.5,2, 96));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.267190, 104.346490), new Coord(52.267190, 104.346490), 10, 10.5,2, 97));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.266970, 104.346530), new Coord(52.266970, 104.346530), 10, 10.5,2, 98));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.266820, 104.346580), new Coord(52.266820, 104.346580), 10, 10.5,2, 99));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.266630, 104.346660), new Coord(52.266630, 104.346660), 10, 10.5,2, 100));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.266460, 104.346740), new Coord(52.266460, 104.346740), 10, 10.5,2, 101));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.265880, 104.347060), new Coord(52.265880, 104.347060), 10, 10.5,2, 102));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.265590, 104.347290), new Coord(52.265590, 104.347290), 10, 10.5,2, 103));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.265260, 104.347630), new Coord(52.265260, 104.347630), 10, 10.5,2, 104));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.264760, 104.348120), new Coord(52.264760, 104.348120), 10, 10.5,2, 105));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.263830, 104.349030), new Coord(52.263830, 104.349030), 10, 10.5,2, 106));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.263320, 104.349560), new Coord(52.263320, 104.349560), 10, 10.5,2, 107));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.262870, 104.349970), new Coord(52.262870, 104.349970), 10, 10.5,2, 108));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.262430, 104.350240), new Coord(52.262430, 104.350240), 10, 10.5,2, 109));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.262020, 104.350390), new Coord(52.262020, 104.350390), 10, 10.5,2, 110));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.261510, 104.350480), new Coord(52.261510, 104.350480), 10, 10.5,2, 111));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.261070, 104.350480), new Coord(52.261070, 104.350480), 10, 10.5,2, 112));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.260660, 104.350400), new Coord(52.260660, 104.350400), 10, 10.5,2, 113));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.260110, 104.350170), new Coord(52.260110, 104.350170), 10, 10.5,2, 114));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.259970, 104.350060), new Coord(52.259970, 104.350060), 10, 10.5,2, 115));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.258350, 104.348100), new Coord(52.258350, 104.348100), 10, 10.5,2, 116));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.258350, 104.348100), new Coord(52.258350, 104.348100), 10, 10.5,2, 117));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.257720, 104.347360), new Coord(52.257720, 104.347360), 10, 10.5,2, 118));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.257310, 104.346840), new Coord(52.257310, 104.346840), 10, 10.5,2, 119));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.254210, 104.342960), new Coord(52.254210, 104.342960), 10, 10.5,2, 120));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.254100, 104.342780), new Coord(52.254100, 104.342780), 10, 10.5,2, 121));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.254040, 104.342670), new Coord(52.254040, 104.342670), 10, 10.5,2, 122));
//        segments.add(new Segment("Airport-Shiryamova_Koljo", new Coord(52.253970, 104.342500), new Coord(52.253970, 104.342500), 10, 10.5,2, 123));
        return Utils.algorithm(cars, segments);
    }

}
