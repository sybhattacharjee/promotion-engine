package com.sample.promotionengine.exception;

/**
 * The Class BusinessException.
 */
public class BusinessException extends RuntimeException {
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6313964237148798850L;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param err the err
	 */
	public BusinessException(Throwable err) {
	    super(err);
	}
	
	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorMessage the error message
	 */
	public BusinessException(String errorMessage) {
	    super(errorMessage);
	}
	
	/**
	 * Instantiates a new business exception.
	 *
	 * @param errorMessage the error message
	 * @param err the err
	 */
	public BusinessException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}

}
