package in.sayes.nestorapp.chat.helper.model;

import java.util.List;

/**
 * Created by sourav on 03/05/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.gson
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class GSON_RootLevel {


    /**
     * failure_message :
     * next_endpoint : /parse_investments
     * questions : [{"input_form":{"type":"floating"},"options":[{"followup":[],"value":"no"},{"followup":[{"input_form":{"type":"grid"},"name_var":"","options":[{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"epf","options":[],"select_type":"single","statements":["How much have you invested in EPF"]}],"value":"EPF"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"ppf","options":[],"select_type":"single","statements":["How much have you invested in PPF"]}],"value":"PPF"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"fd","options":[],"select_type":"single","statements":["How much have you invested in FD"]}],"value":"FD"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"nsc","options":[],"select_type":"single","statements":["How much have you invested in NSC"]}],"value":"NSC"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"nps","options":[],"select_type":"single","statements":["How much have you invested in NPS"]}],"value":"NPS"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"ulips","options":[],"select_type":"single","statements":["How much have you invested in Ulips"]}],"value":"ULIPS"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"elss","options":[],"select_type":"single","statements":["How much have you invested in ELSS"]}],"value":"ELSS"}],"select_type":"multi","statements":["Which all instruments you have invested in?"]}],"value":"yes"}],"select_type":"single","statements":["Have you invested in Tax Saving Investments before?"]}]
     * status : success
     * user_id : 9972210077
     */

    private String failure_message;
    private String next_endpoint;
    private String status;
    private String user_id;


    /**
     * input_form : {"type":"floating"}
     * options : [{"followup":[],"value":"no"},{"followup":[{"input_form":{"type":"grid"},"name_var":"","options":[{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"epf","options":[],"select_type":"single","statements":["How much have you invested in EPF"]}],"value":"EPF"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"ppf","options":[],"select_type":"single","statements":["How much have you invested in PPF"]}],"value":"PPF"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"fd","options":[],"select_type":"single","statements":["How much have you invested in FD"]}],"value":"FD"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"nsc","options":[],"select_type":"single","statements":["How much have you invested in NSC"]}],"value":"NSC"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"nps","options":[],"select_type":"single","statements":["How much have you invested in NPS"]}],"value":"NPS"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"ulips","options":[],"select_type":"single","statements":["How much have you invested in Ulips"]}],"value":"ULIPS"},{"followup":[{"input_form":{"type":"numeric_keyboard"},"name_var":"elss","options":[],"select_type":"single","statements":["How much have you invested in ELSS"]}],"value":"ELSS"}],"select_type":"multi","statements":["Which all instruments you have invested in?"]}],"value":"yes"}]
     * select_type : single
     * statements : ["Have you invested in Tax Saving Investments before?"]
     */

    private List<QuestionsEntity> questions;

    public String getFailure_message() {
        return failure_message;
    }

    public void setFailure_message(String failure_message) {
        this.failure_message = failure_message;
    }

    public String getNext_endpoint() {
        return next_endpoint;
    }

    public void setNext_endpoint(String next_endpoint) {
        this.next_endpoint = next_endpoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<QuestionsEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsEntity> questions) {
        this.questions = questions;
    }

    public static class QuestionsEntity {
        /**
         * type : floating
         */

        private InputFormEntity input_form;
        private String select_type;
        private String name_var;

        /**
         * followup : []
         * value : no
         */

        private List<OptionsEntity> options;
        private List<String> statements;

        public InputFormEntity getInput_form() {
            return input_form;
        }

        public void setInput_form(InputFormEntity input_form) {
            this.input_form = input_form;
        }

        public String getSelect_type() {
            return select_type;
        }

        public void setSelect_type(String select_type) {
            this.select_type = select_type;
        }

        public List<OptionsEntity> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsEntity> options) {
            this.options = options;
        }

        public List<String> getStatements() {
            return statements;
        }

        public void setStatements(List<String> statements) {
            this.statements = statements;
        }

        public String getName_var() {
            return name_var;
        }

        public void setName_var(String name_var) {
            this.name_var = name_var;
        }



    }
    public static class InputFormEntity {
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class OptionsEntity {
        private String value;
        private List<QuestionsEntity> followup;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<QuestionsEntity> getFollowup() {
            return followup;
        }

        public void setFollowup(List<QuestionsEntity> followup) {
            this.followup = followup;
        }
    }


    public static class QuestionList{
        private List<QuestionsEntity> questions;
        private int qustionIndex=0;

        public QuestionList(List<QuestionsEntity> questions, int qustionIndex) {
            this.questions = questions;
            this.qustionIndex = qustionIndex;
        }

        public List<QuestionsEntity> getQuestions() {
            return questions;
        }

        public void setQuestions(List<QuestionsEntity> questions) {
            this.questions = questions;
        }

        public int getQustionIndex() {
            return qustionIndex;
        }

        public void setQustionIndex(int qustionIndex) {
            this.qustionIndex = qustionIndex;
        }


    }
}