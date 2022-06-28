package com.ivman.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.ivman.model.FileUploadModel;
import com.ivman.model.UserRoleModel;
import com.ivman.to.UserRoleTO;
import com.ivman.to.UserTO;

public class JsonConvertorUtility {

	public static String convertUserModelToJSON(List<UserTO> userModelList) {
		String jsonOutput = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

			userModelList.stream().forEach(userModel -> {
				if (userModel.getUserRoleTO() != null) {
					userModel.setUserRoleId(userModel.getUserRoleTO().getUserRoleId().toString());
					userModel.setUserRoleTO(null);
				}
			});

			jsonOutput = objectMapper.writeValueAsString(userModelList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static File convertJsontoCSVFile(String fileName, String jsonValue) {
		JSONObject output;
		File file = new File(fileName);
		try {
			JSONArray jsonArray = new JSONArray(jsonValue);
			String csv = CDL.toString(jsonArray);
			FileUtils.writeStringToFile(file, csv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	private static File getFileFromFileData(FileUploadModel fileData, MultipartFile multipartFile) throws IOException {
		File file;
		if (multipartFile.getSize() > 0) {
			String fileName = multipartFile.getOriginalFilename();
			String prefix = fileName.substring(fileName.lastIndexOf("."));
			file = File.createTempFile(fileName, prefix);
			multipartFile.transferTo(file);
		} else {
			file = new File(fileData.getFilePath());
		}
		return file;
	}

	private static List<Map<?, ?>> convertFileToCSV(File file) throws IOException {
		CsvSchema csv = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv).readValues(file);
		List<Map<?, ?>> list = mappingIterator.readAll();
		return list;
	}

	public static List<UserTO> convertCSVFileToJsonUser(FileUploadModel fileData) {
		List<UserTO> userModelList = new ArrayList<UserTO>();
		MultipartFile multipartFile = fileData.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileData, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				UserTO userTO = new UserTO();
				userTO.setUserId(map.get("userId").toString());
				userTO.setUsername(map.get("username").toString());
				userTO.setFirebaseId(map.get("firebaseId").toString());
				userTO.setFirebaseToken(map.get("firebaseToken").toString());
				UserRoleTO userRoleTO = new UserRoleTO();
				userRoleTO.setUserRoleId(map.get("userRoleId").toString());
				userTO.setUserRoleTO(userRoleTO);
				userTO.setPhoneNumber(map.get("phoneNumber").toString());
				userModelList.add(userTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (multipartFile.getSize() > 0) {
				// After operating the above files, you need to delete the temporary files
				// generated in the root directory
				File f = new File(file.toURI());
				f.delete();
			}
		}
		return userModelList;
	}

//	public static List<UserRoleTO> convertCSVFileToJsonUserRole(FileUploadModel fileUploadModel) {
//		List<UserRoleTO> userRoleModelList = new ArrayList<UserRoleTO>();
//		MultipartFile multipartFile = fileUploadModel.getFileData();
//		File file = null;
//		try {
//			file = getFileFromFileData(fileUploadModel, multipartFile);
//			List<Map<?, ?>> list = convertFileToCSV(file);
//
//			for (Map<?, ?> map : list) {
//				UserRoleTO userRoleModel = new UserRoleTO();
//				userRoleModel.setUserRoleId(map.get("userRoleId").toString());
//				userRoleModel.setUserRoleDesc(map.get("userRoleDesc").toString());
//				userRoleModelList.add(userRoleModel);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (multipartFile.getSize() > 0) {
//				File f = new File(file.toURI());
//				f.delete();
//			}
//			// After operating the above files, you need to delete the temporary files
//			// generated in the root directory
//
//		}
//		return userRoleModelList;
//	}
//
//	public static String convertUserRoleModelToJSON(List<UserRoleTO> userRoleModelList) {
//		String jsonOutput = null;
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//			jsonOutput = objectMapper.writeValueAsString(userRoleModelList);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonOutput;
//	}

}
