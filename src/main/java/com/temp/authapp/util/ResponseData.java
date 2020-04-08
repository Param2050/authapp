package com.temp.authapp.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Validated
@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> implements Serializable {
    private boolean success;
    private String errorMessage;
    private int statusCode;
    private T result;

    public ResponseData(T result) {
        this.result = result;
        this.success = true;
    }

    public static <T> ResponseData.ResponseDataBuilder<T> builder() {
        return new ResponseData.ResponseDataBuilder();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public T getResult() {
        return this.result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseData)) {
            return false;
        } else {
            ResponseData<?> other = (ResponseData)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else {
                label41: {
                    Object this$errorMessage = this.getErrorMessage();
                    Object other$errorMessage = other.getErrorMessage();
                    if (this$errorMessage == null) {
                        if (other$errorMessage == null) {
                            break label41;
                        }
                    } else if (this$errorMessage.equals(other$errorMessage)) {
                        break label41;
                    }

                    return false;
                }

                if (this.getStatusCode() != other.getStatusCode()) {
                    return false;
                } else {
                    Object this$result = this.getResult();
                    Object other$result = other.getResult();
                    if (this$result == null) {
                        if (other$result != null) {
                            return false;
                        }
                    } else if (!this$result.equals(other$result)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResponseData;
    }

    public String toString() {
        return "ResponseData(success=" + this.isSuccess() + ", errorMessage=" + this.getErrorMessage() + ", statusCode=" + this.getStatusCode() + ", result=" + this.getResult() + ")";
    }

    public ResponseData() {
    }

    public ResponseData(boolean success, String errorMessage, int statusCode, T result) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
        this.result = result;
    }

    public static class ResponseDataBuilder<T> {
        private boolean success;
        private String errorMessage;
        private int statusCode;
        private T result;

        ResponseDataBuilder() {
        }

        public ResponseData.ResponseDataBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ResponseData.ResponseDataBuilder<T> errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ResponseData.ResponseDataBuilder<T> statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseData.ResponseDataBuilder<T> result(T result) {
            this.result = result;
            return this;
        }

        public ResponseData<T> build() {
            return new ResponseData(this.success, this.errorMessage, this.statusCode, this.result);
        }

        public String toString() {
            return "ResponseData.ResponseDataBuilder(success=" + this.success + ", errorMessage=" + this.errorMessage + ", statusCode=" + this.statusCode + ", result=" + this.result + ")";
        }
    }
}
