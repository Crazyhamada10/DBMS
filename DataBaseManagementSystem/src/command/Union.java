package command;


public class Union extends Command{

	private ICommand select1;
	private ICommand select2;
	private boolean distinct;
	
	public Union() {
		select1 = new Select();
		select2 = new Select();
		distinct = true;
	}
	
	@Override
	public void setSelectOne(ICommand select1) {
		this.select1 = select1;
	}

	@Override
	public ICommand getSelectOne() {
		return select1;
	}
	
	@Override
	public void setSelectTwo(ICommand select2) {
		this.select2 = select2;
	}
	
	@Override
	public ICommand getSelectTwo() {
		return select2;
	}	
	
	@Override
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	@Override
	public boolean isDistinct() {
		return distinct;
	}

	@Override
	public String toString() {
		return new String(super.toString() + "Distinct: " + isDistinct() + "\n" 
					    + "SelectOne:\n" + select1 + "SelectTwo:\n" + select2);
	}

}
