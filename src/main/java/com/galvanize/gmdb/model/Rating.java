package com.galvanize.gmdb.model;

public class Rating {
	
	private Integer star;
	private String reviewDetail;
	
	public Rating() {
		super();
	}

	public Rating(Integer star, String reviewDetails) {
		super();
		this.star = star;
		this.reviewDetail = reviewDetails;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getReviewDetails() {
		return reviewDetail;
	}

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetail = reviewDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reviewDetail == null) ? 0 : reviewDetail.hashCode());
		result = prime * result + ((star == null) ? 0 : star.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (reviewDetail == null) {
			if (other.reviewDetail != null)
				return false;
		} else if (!reviewDetail.equals(other.reviewDetail))
			return false;
		if (star == null) {
			if (other.star != null)
				return false;
		} else if (!star.equals(other.star))
			return false;
		return true;
	}
	
}
