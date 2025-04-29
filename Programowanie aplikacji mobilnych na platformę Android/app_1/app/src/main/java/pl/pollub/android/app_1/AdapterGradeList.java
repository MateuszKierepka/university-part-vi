package pl.pollub.android.app_1;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pollub.android.app_1.databinding.SubjectRowBinding;

// adapter dla RecyclerView ktory wyswietla liste przedmiotow i umozliwia wybor oceny dla kazdego z nich
public class AdapterGradeList extends RecyclerView.Adapter<AdapterGradeList.SubjectViewHolder> {
    private final List<SubjectGrade> subjectGradeList;
    private LayoutInflater inflater;

    public AdapterGradeList(LayoutInflater inflater, List<SubjectGrade> subjectGradeList) {
        this.inflater = inflater;
        this.subjectGradeList = subjectGradeList;
    }

    // tworzenie nowego widoku dla pojedynczego przedmiotu w liscie
    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.inflater = LayoutInflater.from(parent.getContext());
        return new SubjectViewHolder(SubjectRowBinding.inflate(inflater));
    }

    // wiaze dane przedmiotu z odpowiednimi widokami w wierszu
    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        SubjectGrade currentSubject = this.subjectGradeList.get(position);
        holder.binding.tvSubject.setText(currentSubject.getName());
        //holder.binding.grSubjectGrade.setTag(currentSubject);
        holder.currentSubject = currentSubject;
        switch (currentSubject.getGrade()){
            case 2:
                holder.binding.rbGrade2.setChecked(true);
                break;
            case 3:
                holder.binding.rbGrade3.setChecked(true);
                break;
            case 4:
                holder.binding.rbGrade4.setChecked(true);
                break;
            case 5:
                holder.binding.rbGrade5.setChecked(true);
                break;
        }
    }

    // zwraca liczbe przedmiotow wyswietlanych przez adapter
    @Override
    public int getItemCount() {
        return this.subjectGradeList != null ? this.subjectGradeList.size() : 0;
    }

    // przechowuje widoki zwiazane z przedmiotem oraz obsluguje interakcje z uzytkownikiem takie jak wybor oceny
    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        private SubjectRowBinding binding;
        private SubjectGrade currentSubject;

        // nasluchuje zmiany oceny w radio group
        public SubjectViewHolder(@NonNull SubjectRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.grSubjectGrade.setOnCheckedChangeListener((radioGroup, i) -> {
                //SubjectGrade currentSubject = (SubjectGrade)radioGroup.getTag();
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_grade_2){
                    this.currentSubject.setGrade(2);
                } else if(radioGroup.getCheckedRadioButtonId() == R.id.rb_grade_3){
                    this.currentSubject.setGrade(3);
                } else if(radioGroup.getCheckedRadioButtonId() == R.id.rb_grade_4){
                    this.currentSubject.setGrade(4);
                } else if(radioGroup.getCheckedRadioButtonId() == R.id.rb_grade_5){
                    this.currentSubject.setGrade(5);
                }

            });
        }
    }
}
