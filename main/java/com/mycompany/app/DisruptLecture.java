public class DisruptLecture {
    public static void main(String[] args) {
    CellPhone noiseMaker = new CellPhone();
    ObnoxiousTune ot = new ObnoxiousTune();
    noiseMaker.ring(ot); // ot works though Tune called for
    }
   }