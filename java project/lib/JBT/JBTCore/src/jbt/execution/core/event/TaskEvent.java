/*
 * Copyright (C) 2012 Ricardo Juan Palma Durán
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jbt.execution.core.event;

import java.util.EventObject;

import jbt.execution.core.ExecutionTask;
import jbt.execution.core.ExecutionTask.Status;

/**
 * A TaskEvent is an event that is generated by tasks ({@link ExecutionTask}) to
 * signal that a relevant change in the status of a task has taken place.
 * 
 * @author Ricardo Juan Palma Durán
 * 
 */
public class TaskEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	/** The new status of the task. */
	private Status newStatus;
	/** The previous status of the task. */
	private Status previousStatus;

	/**
	 * Creates a TaskEvent with a particular ExcutionTask as source of the
	 * event. The source (<code>source</code>) is the task whose status has
	 * changed, and <code>newStatus</code> is the new status of the task.
	 * 
	 * @param source
	 *            the task whose status has changed.
	 * @param newStatus
	 *            the new status of the task.
	 */
	public TaskEvent(ExecutionTask source, Status newStatus, Status previousStatus) {
		super(source);
		this.newStatus = newStatus;
		this.previousStatus = previousStatus;
	}

	/**
	 * Returns the new status associated to the task.
	 * 
	 * @return the new status associated to the task.
	 */
	public Status getNewStatus() {
		return this.newStatus;
	}

	/**
	 * Returns the previous status associated to the task.
	 * 
	 * @return the previous status associated to the task.
	 */
	public Status getPreviousStatus() {
		return this.previousStatus;
	}
}
