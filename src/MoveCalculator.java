//import lenz.htw.sawhian.Move;
//
//public class MoveCalculator {
//	
//	
//	 public Move gespeicherterZug = null;
//	 int gewuenschteTiefe = 4;
//	 int bewertung = miniMax(player, gewuenschteTiefe, Integer.MIN_VALUE, Integer.MAX_VALUE);
//	 if (gespeicherterZug == NULL)
//	    es gab keine weiteren Zuege mehr; // Randbedingung
//	 else
//		 gespeicherterZug ausführen; // an Server senden
//	 
//	 
//	 int miniMax(int spieler, int tiefe, int alpha, int beta) {
//		 if (tiefe == 0 or keineZuegeMehr(spieler)) {
//			 return bewerten(spieler);
//		 }
//       
//		 int maxWert = alpha;
//    generiereMoeglicheZuege(spieler);
//    while (noch Zug da) {
//       fuehreNaechstenZugAus();
//       int wert = -miniMax(-spieler, tiefe-1,
//                           -beta, -maxWert);
//       macheZugRueckgaengig();
//       if (wert > maxWert) {
//          maxWert = wert;
//          if (maxWert >= beta)
//             break;
//          if (tiefe == anfangstiefe)
//             gespeicherterZug = Zug;
//       }
//    }
//    return maxWert;
// }
//	
//	
//	
//
//}
