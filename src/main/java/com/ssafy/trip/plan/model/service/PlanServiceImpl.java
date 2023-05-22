package com.ssafy.trip.plan.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.plan.model.dto.GoodPlanDto;
import com.ssafy.trip.plan.model.dto.PlaceDto;
import com.ssafy.trip.plan.model.dto.PlanDto;
import com.ssafy.trip.plan.model.dto.PlanJoinGoodDto;
import com.ssafy.trip.plan.model.mapper.PlanMapper;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class PlanServiceImpl implements PlanService {
	
	private PlanMapper planMapper;

	public PlanServiceImpl(PlanMapper planMapper) {
		super();
		this.planMapper = planMapper;
	}

	private static int[] pick, resPick;
	private static boolean[] visited;
	private static long[][] D;
	private static long minSum;

	@Override
	public void deletePlan(int id) throws Exception {
		planMapper.deletePlan(id);
	}

	@Override
	public void deletePlace(int planId) throws Exception {
		planMapper.deletePlace(planId);
	}


	@Override
	public List<PlaceDto> getPlace(int planId) throws SQLException {
		return planMapper.selectPlace(planId);
	}

	@Override
	public int getPlanId(Map<String, Object> param) throws SQLException {
		return planMapper.selectPlanId(param);
	}

	@Override
	public void updateHit(int planId) throws SQLException {
		planMapper.updateHit(planId);
	}


	/** 알고리즘 적용 - 완전탐색 (순열) */
	public List<PlaceDto> selectFastDistancePlace(int planId) throws SQLException {
		List<PlaceDto> list = getPlace(planId);
		List<PlaceDto> resultList = new ArrayList<>();
		int cnt = planMapper.getTotalPlaceCount(planId);
		// 최단 거리 알고리즘 사용 : 완전 탐색
		D = new long[cnt][cnt];

		// 서로 간의 거리 구하기
		for (int i = 0; i < cnt; i++) {
			for (int j = i + 1; j < cnt; j++) {
				PlaceDto start = list.get(i);
				PlaceDto end = list.get(j);

				long startLat = (long) (start.getLat().doubleValue() * Math.pow(10, 13));
				long endLat = (long) (end.getLat().doubleValue() * Math.pow(10, 13));
				long startLng = (long) (start.getLng().doubleValue() * Math.pow(10, 13));
				long endLng = (long) (end.getLng().doubleValue() * Math.pow(10, 13));

				D[i][j] = Math.abs(startLat - endLat) + Math.abs(startLng - endLng);
				D[j][i] = D[i][j];
			}
		}

		// 초기화
		visited = new boolean[cnt];
		pick = new int[cnt];
		resPick = new int[cnt];
		minSum = Long.MAX_VALUE;
		recur(0, cnt);

		for (int i = 0; i < cnt; i++) {
			int idx = resPick[i];
			resultList.add(list.get(idx));
		}
		return resultList;
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if("userid".equals(key))
			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = planMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	// api
	
	/**
	 * 여행 경로 목록 출력
	 */
	@Override
	public List<PlanDto> getPlanList() throws Exception {
		return planMapper.selectPlanList();
	}

	/**
	 * 여행 계획 추가
	 */
	@Override
	public void registPlan(PlanDto planDto) throws Exception {
		planMapper.insertPlan(planDto);
	}
	
	@Override
	public void registPlace(PlaceDto placeDto) throws Exception {
		planMapper.insertPlace(placeDto);
	}

	@Override
	public PlanDto getPlanOne(int planId) throws Exception {
		return planMapper.selectPlanOne(planId);
	}

	@Override
	public List<PlaceDto> getPlaceList(int planId) throws Exception {
		return planMapper.selectPlace(planId);
	}

	@Override
	public List<PlaceDto> getFastDistancePlace(int planId) throws Exception {
		List<PlaceDto> list = getPlace(planId);
		List<PlaceDto> resultList = new ArrayList<>();
		int cnt = planMapper.getTotalPlaceCount(planId);
		// 최단 거리 알고리즘 사용 : 완전 탐색
		D = new long[cnt][cnt];

		// 서로 간의 거리 구하기
		for (int i = 0; i < cnt; i++) {
			for (int j = i + 1; j < cnt; j++) {
				PlaceDto start = list.get(i);
				PlaceDto end = list.get(j);

				long startLat = (long) (start.getLat().doubleValue() * Math.pow(10, 13));
				long endLat = (long) (end.getLat().doubleValue() * Math.pow(10, 13));
				long startLng = (long) (start.getLng().doubleValue() * Math.pow(10, 13));
				long endLng = (long) (end.getLng().doubleValue() * Math.pow(10, 13));

				D[i][j] = Math.abs(startLat - endLat) + Math.abs(startLng - endLng);
				D[j][i] = D[i][j];
			}
		}

		// 초기화
		visited = new boolean[cnt];
		pick = new int[cnt];
		resPick = new int[cnt];
		minSum = Long.MAX_VALUE;
		recur(0, cnt);

		for (int i = 0; i < cnt; i++) {
			int idx = resPick[i];
			resultList.add(list.get(idx));
		}
		return resultList;
	}

	@Override
	public void modifyPlan(PlanDto planDto) throws Exception {
		planMapper.updatePlan(planDto);
	}
	
	public static void recur(int cnt, int n) {
		if (cnt == n) {
			fastSequence(n);
			return;
		}

		for (int i = 0; i < n; i++) {
			if (visited[i])
				continue;
			visited[i] = true;
			pick[cnt] = i;
			recur(cnt + 1, n);
			visited[i] = false;
		}
	}

	public static void fastSequence(int n) {
		long sum = 0;
		for (int i = 0; i < n - 1; i++) {
			sum += D[pick[i]][pick[i + 1]];
		}

		if (sum < minSum) {
			minSum = sum;

			for (int i = 0; i < n; i++) {
				resPick[i] = pick[i];
			}
		}
	}

	@Override
	public int getGoodPlanNum(Map<String, Object> map) throws SQLException {
		return planMapper.getGoodPlanNum(map);
	}

	@Override
	public List<PlanJoinGoodDto> getGoodPlan(String userId) throws SQLException {
		return planMapper.getGoodPlan(userId);
	}

	@Override
	public void registGoodPlan(GoodPlanDto goodPlanDto) throws SQLException {
		planMapper.insertGoodPlan(goodPlanDto);
	}

	@Override
	public void deleteGoodPlan(Map<String, Object> map) throws SQLException {
		planMapper.deleteGoodPlan(map);
	}
}
