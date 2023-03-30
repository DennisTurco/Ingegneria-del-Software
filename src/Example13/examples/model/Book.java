/*
 * Book
 *
 * (c) 2021-2022 Federico Bergenti. All Rights Reserved.
 */
package Example13.examples.model;

import Example13.beans.Bean;

public interface Book extends Bean {
	public int getID();
	public String getAuthor();
	public String getTitle();
}
