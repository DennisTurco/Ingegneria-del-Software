package Example09;

import java.util.List;

import javax.sound.midi.VoiceStatus;

public class Example09 {
	private void go() {
		try {
			BeanLoader loader = new BeanLoader();
			
			List<SimpleStudent> studentBeanSimple = loader.load(SimpleStudent.class, "Student.csv");
			
			for (Bean bean : bookBean) {
				System.out.println(bean);
			}
			
		} catch() {
			
		}
	}
	
	public static final void main(String[] args) {
		new Example09().go();
	}
}
