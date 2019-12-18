import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double[] arr = {4.1, 23.1, 12.1, 16.1, 91.1, 59.1, 73.1, 28.1, 33.1, 41.1};
		Arrays.sort(arr);
		Arrays.sort(arr, Collections.reverseOrder());
  
		for(int i = 0 ; i < arr.length ; i ++)
		{
			System.out.println(arr[i]);
		}
	}

}
