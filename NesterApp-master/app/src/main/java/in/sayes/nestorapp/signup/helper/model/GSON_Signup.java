package in.sayes.nestorapp.signup.helper.model;

import java.util.List;

/**
 * Created by sourav on 03/05/16.
 * Project : NesterApp , Package Name : in.sayes.nestorapp.signup.helper.model
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class GSON_Signup {


    /**
     * failure_message :
     * next_endpoint : /root_level
     * questions : [{"input_form":{"type":"cascade_cards"},"name_var":"user_query","options":[{"value":"I am new to tax saving investment. Please start from scratch."},{"value":"I pay rent. Can you help me with HRA exemption?"},{"value":"I always feel my in hand salary is way less than my CTC."},{"value":"I have done investment in PPF. Can I take benefit?"},{"value":"I have done investment in PF. Can I get benefit?"}],"select_type":"single","statements":["This is Nestor!","Welcome, select the option you care about?"]},{"input_form":{"height":1,"type":"custom_keyboard","width":1},"name_var":"user_start","options":["Lets start to plan it"],"select_type":"single","statements":["Cool, like everything else this too needs some planning."]}]
     * status : success
     * user_id : 9972210077
     */

    private String failure_message;
    private String next_endpoint;
    private String status;
    private String user_id;



    private String message;
    /**
     * input_form : {"type":"cascade_cards"}
     * name_var : user_query
     * options : [{"value":"I am new to tax saving investment. Please start from scratch."},{"value":"I pay rent. Can you help me with HRA exemption?"},{"value":"I always feel my in hand salary is way less than my CTC."},{"value":"I have done investment in PPF. Can I take benefit?"},{"value":"I have done investment in PF. Can I get benefit?"}]
     * select_type : single
     * statements : ["This is Nestor!","Welcome, select the option you care about?"]
     */

    private List<QuestionsEntity> questions;

    public String getFailure_message() {
        return failure_message;
    }

    public void setFailure_message(String failure_message) {
        this.failure_message = failure_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
         * type : cascade_cards
         */

        private InputFormEntity input_form;
        private String name_var;
        private String select_type;
        /**
         * value : I am new to tax saving investment. Please start from scratch.
         */

        private List<OptionsEntity> options;
        private List<String> statements;

        public InputFormEntity getInput_form() {
            return input_form;
        }

        public void setInput_form(InputFormEntity input_form) {
            this.input_form = input_form;
        }

        public String getName_var() {
            return name_var;
        }

        public void setName_var(String name_var) {
            this.name_var = name_var;
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

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
