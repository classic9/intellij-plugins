/*
 * Copyright (C) 2020 ThoughtWorks, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.thoughtworks.gauge;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.thoughtworks.gauge.core.Gauge;
import com.thoughtworks.gauge.core.GaugeVersion;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static com.thoughtworks.gauge.Constants.MIN_GAUGE_VERSION;
import static com.thoughtworks.gauge.util.GaugeUtil.isGaugeProjectDir;

public final class GaugeProjectListener implements ProjectManagerListener {
  private static final Logger LOG = Logger.getInstance(GaugeProjectListener.class);

  @Override
  public void projectOpened(@NotNull Project project) {
    if (isGaugeProjectDir(new File(project.getBasePath()))) {
      if (!GaugeVersion.isGreaterOrEqual(MIN_GAUGE_VERSION, false)) {
        String notificationTitle = String.format("Unsupported Gauge Version(%s)", GaugeVersion.getVersion(false).version);
        String errorMessage = String.format("This version of Gauge Intellij plugin only works with Gauge version >= %s", MIN_GAUGE_VERSION);
        LOG.debug(String.format("%s\n%s", notificationTitle, errorMessage));
        Notification notification = new Notification("Error", notificationTitle, errorMessage, NotificationType.ERROR);
        Notifications.Bus.notify(notification, project);
      }
    }
  }

  @Override
  public void projectClosed(@NotNull Project project) {
    for (Module module : ModuleManager.getInstance(project).getModules()) {
      Gauge.disposeComponent(module);
    }
  }
}
