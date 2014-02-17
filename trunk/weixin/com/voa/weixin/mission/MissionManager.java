/**
  	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.voa.weixin.mission;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.voa.weixin.utils.WeixinUtils;

/**
 * 周期任务管理器
 * @author zhaiyuxin
 *
 */
public class MissionManager {

	private final Log logger = LogFactory.getLog(MissionManager.class);
	
	/** 时间器，用来定时完成周期任务 **/
	private final Timer timer;
	private final Map<String, Mission> missions;
	private static MissionManager manager;

	private MissionManager() {
		timer = new Timer();
		missions = new HashMap<String, Mission>();

		init();
	}

	private void init() {
		Document doc = null;
		try {
			doc = WeixinUtils.getDocumentResource("weixin.mission.xml");

		} catch (Exception e) {
			logger.warn("can't find or parser weixin.mission.xml:"
					+ e.getMessage());
		}
		if (doc == null)
			return;

		List<Element> missionEs = doc.getRootElement().elements("mission");

		for (Element missionE : missionEs) {
			String name = missionE.elementText("name");
			String period = missionE.elementText("period");
			String delay = missionE.elementText("delay");
			String className = missionE.elementText("class");

			try {
				Class clz = Class.forName(className);
				Constructor<Mission> constructor = clz
						.getConstructor(String.class);

				Mission mission = constructor.newInstance(name);

				mission.setDelay((Integer.valueOf(delay) * 1000));
				mission.setPeriod((Integer.valueOf(period) * 1000));

				missions.put(name, mission);

				timer
						.schedule(mission, mission.getDelay(), mission
								.getPeriod());
			} catch (Exception e) {
				logger.warn("mission can't be instance : " + name);
				logger.warn("cause by " + e.getMessage());
			}
		}

	}

	public static MissionManager getInstance() {
		if (manager == null)
			manager = new MissionManager();

		return manager;
	}

	public Timer getTimer() {
		return timer;
	}

	public void addMission(Mission mission) {
		missions.put(mission.getName(), mission);
		timer.schedule(mission, mission.getDelay(), mission.getPeriod());
	}

	public Mission getMission(String name) {
		return missions.get(name);
	}
	
	public void destroy(){
		timer.cancel();
	}
}
