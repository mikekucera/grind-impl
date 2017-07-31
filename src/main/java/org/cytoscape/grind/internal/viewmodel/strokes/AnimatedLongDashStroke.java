package org.cytoscape.grind.viewmodel.strokes;

/*
 * #%L
 * Cytoscape Ding View/Presentation Impl (ding-presentation-impl)
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2006 - 2013 The Cytoscape Consortium
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.awt.BasicStroke;

public class AnimatedLongDashStroke extends BasicStroke implements WidthStroke, AnimatedStroke {

	static float nsteps = 4.0f;
	float width;
	float offset;

	public AnimatedLongDashStroke(float width) {
		super(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
		      10.0f, new float[]{width * 4f, width * 2f}, 0.0f);
		this.width = width;
		this.offset = -1.0f;
	}

	public AnimatedLongDashStroke(float width, float offset) {
		super(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
		      10.0f, new float[]{width * 4f, width * 2f}, width*6f*offset);
		this.width = width;
		this.offset = offset;
	}

	public WidthStroke newInstanceForWidth(float w) {
		if (offset < 0.0f)
			return new AnimatedLongDashStroke(w);
		else
			return new AnimatedLongDashStroke(w, offset);
	}

	public AnimatedStroke newInstanceForNextOffset() {
		float stepSize = 1.0f/nsteps;
		float newOffset = offset - stepSize;
		if (newOffset < 0)
			newOffset = 1.0f-stepSize;

		return new AnimatedLongDashStroke(width, newOffset);
	}

	public float getOffset() { return offset; }

	@Override public String toString() { return this.getClass().getSimpleName() + " " + Float.toString(width) + " with offset "+offset; }
}


