import java.util.*;

public abstract class Candle implements Storage.Storable {
    public abstract List<Integer> size();





    public static class Small extends Candle {
        public List<Integer> size() {
            return Arrays.asList(1, 1);
        }
    }





    public static class Large extends Candle {
        public List<Integer> size() {
            return Arrays.asList(2, 1);
        }
    }
}
