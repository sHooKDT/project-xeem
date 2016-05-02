package shook.xeem.interfaces;

import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public interface BlankEditor {

    BlankObject.Builder getBuilder();

    void applyQuestionEdit(int index, QuestionObject _question);

    void startQuestionEdit(int index, QuestionObject _question);

}
