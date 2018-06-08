package com.n26.codechallenge.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents a person within the Game of Thrones fantasy world")
@JsonInclude(Include.NON_NULL)
public class Statistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@JsonProperty(value = "sum")
	@ApiModelProperty(notes = "Sum of all transaction amounts in the last 60 seconds", required = true, allowEmptyValue = false, example = "1000")
	private Double sum = 0.0;

	@NotNull
	@JsonProperty(value = "avg")
	@ApiModelProperty(notes = "Average transaction amount in the last 60 seconds", required = true, allowEmptyValue = false, example = "100")
	private Double avg = 0.0;
	
	@NotNull
	@JsonProperty(value = "max")
	@ApiModelProperty(notes = "Single maximum transaction amount in the last 60 seconds", required = true, allowEmptyValue = false, example = "300")
	private Double max = 0.0;
	
	@NotNull
	@JsonProperty(value = "min")
	@ApiModelProperty(notes = "Single minimum transaction amount in the last 60 seconds", required = true, allowEmptyValue = false, example = "12.3")
	private Double min = 0.0;
	
	@NotNull
	@JsonProperty(value = "count")
	@ApiModelProperty(notes = "Number of transactions made in the last 60 seconds", required = true, allowEmptyValue = false, example = "10")
	private Long count = 0L;

	@JsonCreator
	public Statistics(@JsonProperty("sum") Double sum,
			@JsonProperty("avg") Double avg,
			@JsonProperty("max") Double max,
			@JsonProperty("min") Double min,
			@JsonProperty("count") Long count) {
		
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	
	@JsonCreator
	public Statistics() {
	}
		
	
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	public void updateStatistics(Double sum, Double avg, Double max, Double min, Long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	
}
