package nari.MainGridService.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nari.Geometry.Coordinate;
import nari.Geometry.Envelope;
import nari.Geometry.GeometryBuilder;
import nari.Geometry.GeometryType;
import nari.Geometry.Polygon;
import nari.MainGridService.MainGridServiceActivator;
import nari.MemCache.CacheEntry;
import nari.MemCache.CacheService;
import nari.MemCache.Pointer;
import nari.MemCache.Shape;
import nari.MemCache.matcher.QueryMatcher;
import nari.MemCache.matcher.QueryPolygon;
import nari.model.bean.SymbolDef;
import nari.model.device.DefaultDevice;
import nari.model.device.Device;
import nari.model.device.DeviceModel;
import nari.model.device.ModelService;
import nari.model.device.SpatialDevice;
import nari.model.geometry.GeometryService;
import nari.model.symbol.SymbolAdapter;
import nari.parameter.MapService.GetVectorMap.GetVectorMapRequest;
import nari.parameter.MapService.GetVectorMap.GetVectorMapResponse;
import nari.parameter.bean.GeometryPair;
import nari.parameter.bean.PSRCondition;
import nari.parameter.bean.QueryField;
import nari.parameter.bean.QueryRecord;
import nari.parameter.bean.QueryResult;
import nari.parameter.bean.SubPSRCondition;
import nari.parameter.bean.SymbolPair;
import nari.parameter.code.ReturnCode;

public class GetViewGeometryByCacheHandler {
//
//
//	private GeometryService geoService = MainGridServiceActivator.geoService;
//	private DeviceModel model = DeviceModel.NONE;
//	private SymbolAdapter symAdp = MainGridServiceActivator.symboladapter;
//	private ModelService ms = MainGridServiceActivator.modelService;
//	private CacheService cacheService = MainGridServiceActivator.cacheService;
//	
//	public GetVectorMapResponse getVectorMapByCache(GetVectorMapRequest request) {
//		GetVectorMapResponse resp = new GetVectorMapResponse();
//		double[] coords0 = request.getBbox();
//		long start = System.currentTimeMillis();
//		Polygon polygon = geoService.createPolygon(coords0);
//		
//		if (polygon == null) {
//			resp.setCode(ReturnCode.MISSTYPE);
//			return resp;
//		}
//
//		PSRCondition[] classCondition = request.getClassCondition();
//		String[] queryFieldName = request.getRetrunField();
//		if (queryFieldName == null) {
//			queryFieldName = new String[] { "OID", "SBMC", "SHAPE", "SBZLX", "DYDJ", "FHDX" };
//		}
//		boolean isQueryGeometry = true;
//		
//		Envelope enve = polygon.getEnvelope();
//		QueryPolygon poly = new QueryPolygon(enve.getMinX(),enve.getMinY(),enve.getMaxX(),enve.getMaxY());
//		
//		QueryResult[] results = new QueryResult[classCondition.length];
//
//		int count = 0;
//		
//		List<QueryRecord> records = null;
//		
//		for (int i = 0; i < classCondition.length; i++) {
//			
//			String classId = classCondition[i].getClassId();
//			try {
//				model = ms.fromClass(classId, false);
//			} catch (Exception e) {
//				System.out.println("模型创建时出错");
//				resp.setCode(ReturnCode.BUILDMODEL);
//				return resp;
//			}
//
//			SubPSRCondition[] modelCondition = classCondition[i].getModelCondition();
//			
//			if(modelCondition == null){
//				continue;
//			}
//			
//			Map<String,Object> map = null;
//			
//			records = new ArrayList<QueryRecord>();
//			
//			for(SubPSRCondition condition:modelCondition){
//				String sbzlx = condition.getSbzlx();
//				String[] DYDJ = condition.getDYDJ();
//				
//				QueryMatcher matcher = new QueryMatcher("shape", poly);
//				QueryMatcher volMatcher = new QueryMatcher("dydj", DYDJ, "in");
//				matcher.and(volMatcher);
//				
//				try {
//					CacheEntry entry = cacheService.search(sbzlx, matcher);
//					if(entry==null){
//						continue;
//					}
//					count += entry.size();
//					
//					while(entry.hasNext()){
////						Object cache = entry.next();
////						Class<?> clazz = cache.getClass();
//						
//						Pointer ptr = entry.next();
//						
//						map = new HashMap<String,Object>();
//						
//						for(String field:queryFieldName){
////							Field f = clazz.getDeclaredField(field.toLowerCase());
////							f.setAccessible(true);
////							Object val = f.get(cache);
//							Object val = ptr.getFieldValue(field.toLowerCase(), null);
//							if(val.getClass()==Shape.class){
//								Shape shape = (Shape)val;
//								val = GeometryBuilder.getBuilder().buildGeometry(shape.getType(), shape.getElementInfo(), shape.getCoordinates());
////								val = GeometryHelper.buildGeometry(shape.getType(), shape.getElementInfo(), shape.getCoordinates());
//							}
//							map.put(field, val);
//						}
//						Device dev = new DefaultDevice(model.getFieldDef(), model.getClassDef(), model.getSubClassDef(), map);
//						
//						QueryRecord record = processResult(dev,queryFieldName,isQueryGeometry);
//						records.add(record);
//					}
////					String rect = "sdo_relate(shape ,mdsys.sdo_geometry(2003,null,null,mdsys.sdo_elem_info_array(1,1003,1),mdsys.sdo_ordinate_array(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)),'mask=anyinteract querytype=window')='TRUE'";
////					System.out.println(String.format(rect,poly.getMinX(),poly.getMinY(),poly.getMaxX(),poly.getMinY(),poly.getMaxX(),poly.getMaxY(),poly.getMinX(),poly.getMaxY(),poly.getMinX(),poly.getMinY()));
//				} catch (Exception excep) {
//					excep.printStackTrace();
//					resp.setCode(ReturnCode.SQLERROR);
//					return resp;
//				}
//				
//			}
//			
//			QueryRecord[] arr = new QueryRecord[records.size()];
//			arr = records.toArray(arr);
//			results[i] = new QueryResult();
//			results[i].setRecords(arr);
//			results[i].setCount(records.size());
//		}
//		
//		System.out.println("count:"+count);
//		
//		resp.setResult(results);
//		resp.setCode(ReturnCode.SUCCESS);
//		resp.setRequestExtend(request.getRequestExtend());
//		long end = System.currentTimeMillis();
//		System.out.println("每次查询用时:" + (end - start) + "ms");
//		return resp;
//	}
//	
////	private QueryRecord processResult(Object cache ,String[] queryFieldName ,boolean isQueryGeometry) throws Exception{
////		Class<?> clazz = cache.getClass();
////		
////		QueryRecord record = new QueryRecord();
////		
////		if (isQueryGeometry) {
////			Field f = clazz.getDeclaredField("shape");
////			f.setAccessible(true);
////			Object val = f.get(cache);
////			
////			Geometry geom = null;
////			if(f.getType()==Shape.class){
////				Shape shape = (Shape)val;
////				geom = GeometryHelper.buildGeometry(shape.getType(), shape.getElementInfo(), shape.getCoordinates());
////			}
////			if(geom!=null){
////				GeometryPair gp = new GeometryPair();
////				
////				String geometryType = "";
////				GeometryType geomType = geom.getGeometryType();
////				geometryType = geomType.toString();
////				Coordinate[] coordnates = geom.getCoordinates();
////				int natesnum = coordnates.length;
////				
////				double[] coords = new double[2 * natesnum];
////				for (int k = 0; k < natesnum; k++) {
////					coords[2 * k] = coordnates[k].getX();
////					coords[2 * k + 1] = coordnates[k].getY();
////				}
////				gp.setGeometryType(geometryType);
////				gp.setCoords(coords);
////				record.setGeom(gp);
////			}
////		}
////		
////		Arrays.sort(queryFieldName);
////		int index = Arrays.binarySearch(queryFieldName, "SHAPE");
////		
////		int size = index>=0?queryFieldName.length-1:queryFieldName.length;
////		QueryField[] fields = new QueryField[size];
////		int i = 0;
////		QueryField qfield = null;
////		Object val = null;
////		for (String field:queryFieldName) {
////			if (field.equalsIgnoreCase("SHAPE")) {
////				continue;
////			}
////			qfield = new QueryField();
////			qfield.setFieldName(field);
////
////			Field f = clazz.getDeclaredField(field.toLowerCase());
////			f.setAccessible(true);
////			val = f.get(cache);
////			
//////			val = dev.getValue(field.toUpperCase());
////			qfield.setFieldValue(val==null?"":String.valueOf(val));
////			qfield.setFieldAlias(model.getFieldDef().find(field.toUpperCase()).getFieldAlias());
////			fields[i++] = qfield;
////		}
////		
////		record.setFields(fields);
////		
////		Field f = clazz.getDeclaredField("sbzlx");
////		f.setAccessible(true);
////		val = f.get(cache);
////		
////		Iterator<SymbolDef> it = symAdp.search(String.valueOf(val));
////		
////		SymbolDef defaultSymbol = null;
////		
////		SymbolDef symbol = null;
////		if(it != null){
////			while(it.hasNext()){
////				SymbolDef def = it.next();
////				String value = def.getSymbolValue();
////				if(value==null || "".equals(value)){
////					defaultSymbol = def;
////					continue;
////				}
////				
////				if(value.endsWith(";")){
////					value = value.substring(0, value.length()-2);
////				}
////				String[] conditions = value.split(";");
////				boolean b = true;
////				for(String condition:conditions){
////					if(condition==null || "".equals(condition)){
////						continue;
////					}
////					String[] arr = condition.split("=");
////					f = clazz.getDeclaredField(arr[0].toLowerCase());
////					f.setAccessible(true);
////					val = f.get(cache);
////					
////					String devVal = val==null?"":String.valueOf(val);
////					if("".equals(devVal)){
////						continue;
////					}
////					
////					String valr = arr[1];
////					if(devVal.equalsIgnoreCase(valr)){
////						b = b && true;
////					}else{
////						b = b && false;
////						break;
////					}
////				}
////				
////				if(b){
////					symbol = def;
////					break;
////				}
////			}
////			
////			SymbolDef symDef = symbol==null?defaultSymbol:symbol;
////			
////			if (symDef != null) {
////				SymbolPair sp = new SymbolPair();
////				sp.setModelId(symDef.getModelId());
////				sp.setSymbolValue(symDef.getSymbolValue());
////				sp.setSymbolId(symDef.getSymbolId());
////				sp.setDevtypeId(symDef.getDevTypeId());
////				record.setSymbol(sp);
////			}
////		}
////		
////		return record;
////	}
//	
//	private QueryRecord processResult(Device dev,String[] queryFieldName ,boolean isQueryGeometry) throws Exception {
//		QueryRecord record = new QueryRecord();
//		
//		if (isQueryGeometry) {
//			SpatialDevice spaDev = dev.asSpatialDevice();
//			
//			GeometryPair geom = new GeometryPair();
//			
//			String geometryType = "";
//			GeometryType geomType = spaDev.getGeometry().getGeometry().getGeometryType();
//			geometryType = geomType.toString();
//			Coordinate[] coordnates = spaDev.getGeometry().getGeometry().getCoordinates();
//			int natesnum = coordnates.length;
//			
//			double[] coords = new double[2 * natesnum];
//			for (int k = 0; k < natesnum; k++) {
//				coords[2 * k] = coordnates[k].getX();
//				coords[2 * k + 1] = coordnates[k].getY();
//			}
//			geom.setGeometryType(geometryType);
//			geom.setCoords(coords);
//			record.setGeom(geom);
//		}
//		
//		Arrays.sort(queryFieldName);
//		int index = Arrays.binarySearch(queryFieldName, "SHAPE");
//		
//		int size = index>=0?queryFieldName.length-1:queryFieldName.length;
//		QueryField[] fields = new QueryField[size];
//		int i = 0;
//		QueryField qfield = null;
//		Object val = null;
//		for (String field:queryFieldName) {
//			if (field.equalsIgnoreCase("SHAPE")) {
//				continue;
//			}
//			qfield = new QueryField();
//			qfield.setFieldName(field);
//
//			val = dev.getValue(field.toUpperCase());
//			qfield.setFieldValue(val==null?"":String.valueOf(val));
//			qfield.setFieldAlias(model.getFieldDef().find(field.toUpperCase()).getFieldAlias());
//			fields[i++] = qfield;
//		}
//		
//		record.setFields(fields);
//		
//		SymbolDef symDef = symAdp.search(dev);
//		if (symDef != null) {
//			SymbolPair symbol = new SymbolPair();
////			symbol.setModelId(symDef.getModelId());
//			symbol.setSymbolValue(symDef.getSymbolValue());
//			symbol.setSymbolId(symDef.getSymbolId());
////			symbol.setDevtypeId(symDef.getDevTypeId());
//			record.setSymbol(symbol);
//		}
//		return record;
//	}
}