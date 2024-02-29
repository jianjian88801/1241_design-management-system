package com.xunmaw.design.service;

import com.xunmaw.design.domain.FileData;
import com.xunmaw.design.domain.Student;
import com.github.pagehelper.Page;

public interface FileDataService {
    FileData searchAllFileData( Student user);
}
