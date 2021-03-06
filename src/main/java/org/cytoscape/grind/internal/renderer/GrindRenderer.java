package org.cytoscape.grind.renderer;

/*
 * #%L
 * Cytoscape Grind View/Presentation Impl (grind-impl)
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2006 - 2016 The Cytoscape Consortium
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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;

import org.cytoscape.service.util.CyServiceRegistrar;

import org.cytoscape.grind.viewmodel.GrindGraphView;

public class GrindRenderer {
	protected final GrindGraphView view;
	final CyServiceRegistrar registrar;
	static double FRAME_RATE = 1000000.0/60.0; // Frame rate (in us)
	private ScheduledExecutorService loopScheduler;

	public GrindRenderer(GrindGraphView view, CyServiceRegistrar registrar) {
		this.view = view;
		this.registrar = registrar;

		// Create the necessary drawing surfaces

		// Start the rendering engine.  
		startLoop();
	}

	private void startLoop() {
		loopScheduler = Executors.newScheduledThreadPool(1);
		Runnable innerLoop = new Runnable() {
			public void run() {
				renderLoop();
			}
		};
		loopScheduler.scheduleAtFixedRate(innerLoop, 0, (long)FRAME_RATE, MICROSECONDS);
	}

	private void stopLoop() {
		if (loopScheduler != null)
			loopScheduler.shutdown();
	}

	private void renderLoop() {
		// The list of GrindNodeViews is available
		// using view.getGrindNodeViews(), or (optionally)
		// view.getGrindNodeViews(Rectangle2D boundingBox).  The latter is
		// actually for future uses and at this point just returns all nodes.
		//
		// Similarly to get all edge views, you would use view.getGrindEdgeViews().
		// GrindGraphView, GrindNodeView, and GrindEdgeView all implement the View
		// interface, so it's straightforward to get the various visual properties.
		// In the longer term, complicated visual properties (e.g. NODE_LABEL_POSITION) should
		// be calculated and cached in the appropriate view.
		if (view.updateNeeded()) {
			// Render

			view.updateNeeded(false);
		}
	}
}
