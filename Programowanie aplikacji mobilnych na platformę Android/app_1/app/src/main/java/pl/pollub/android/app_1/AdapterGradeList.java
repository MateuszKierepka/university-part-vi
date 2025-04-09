package pl.pollub.android.app_1;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pollub.android.app_1.databinding.SubjectRowBinding;

public class AdapterGradeList extends RecyclerView.Adapter<AdapterGradeList.SubjectViewHolder> {
    private final List<SubjectGrade> subjectGradeList;
    private LayoutInflater inflater;

    public AdapterGradeList(LayoutInflater inflater, List<SubjectGrade> subjectGradeList) {
        this.inflater = inflater;
        this.subjectGradeList = subjectGradeList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.inflater = LayoutInflater.from(parent.getContext());
        return new SubjectViewHolder(SubjectRowBinding.inflate(inflater));
    }

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

    @Override
    public int getItemCount() {
        return this.subjectGradeList != null ? this.subjectGradeList.size() : 0;
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        private SubjectRowBinding binding;
        private SubjectGrade currentSubject;

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
