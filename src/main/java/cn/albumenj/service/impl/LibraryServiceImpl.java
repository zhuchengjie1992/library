package cn.albumenj.service.impl;

import cn.albumenj.bean.Library;
import cn.albumenj.bean.UserSecurity;
import cn.albumenj.dao.LibraryDao;
import cn.albumenj.dto.LibraryDto;
import cn.albumenj.service.LibraryService;
import cn.albumenj.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    LibraryDao libraryDao;

    @Autowired
    UserSecurityService userSecurityService;

    /**
     * 通过ID查找图书馆
     *
     * @param library
     * @return
     */
    @Override
    public LibraryDto selectById(Library library) {
        LibraryDto libraryDto = libraryDao.selectById(library);
        return libraryDto;
    }

    /**
     * 通过馆长ID查找图书馆
     *
     * @param library
     * @return
     */
    @Override
    public List<LibraryDto> selectByCurator(Library library) {
        List<LibraryDto> libraryDtoList = libraryDao.selectByCurator(library);
        if(libraryDtoList.isEmpty()) {
            return null;
        }
        return libraryDtoList;
    }


    /**
     * 有限制性查找图书馆
     *
     * @param library
     * @return
     */
    @Override
    public List<LibraryDto> selectLimit(Library library) {
        List<LibraryDto> libraryDtoList = libraryDao.selectLimit(library);
        if(libraryDtoList.isEmpty()) {
            return null;
        }
        return libraryDtoList;
    }

    /**
     * 获取图书馆数量
     *
     * @param library
     * @return
     */
    @Override
    public int count(Library library) {
        int count = libraryDao.count();

        return count;
    }

    /**
     * 添加图书馆
     *
     * @param library
     * @return
     */
    @Override
    public boolean add(Library library) {
        boolean permissionAdministrator = userSecurityService.checkIdAdministrator(library.getLoginedUserId());
        boolean permissionCurator = userSecurityService.checkIdCurator(library.getCuratorId());
        if(permissionAdministrator && permissionCurator) {
            int row = libraryDao.add(library);
            return (row == 1);
        }
        else {
            return false;
        }
    }

    /**
     * 删除图书馆
     *
     * @param library
     * @return
     */
    @Override
    public boolean delete(Library library) {
        boolean permission = userSecurityService.checkIdAdministrator(library.getLoginedUserId());
        if(permission) {
            int row = libraryDao.delete(library);
            return (row == 1);
        }
        else {
            return false;
        }
    }

    /**
     * 修改图书馆
     *
     * @param library
     * @return
     */
    @Override
    public boolean update(Library library) {
        boolean permissionAdministrator = userSecurityService.checkIdAdministrator(library.getLoginedUserId());
        boolean permissionCurator = userSecurityService.checkIdCurator(library.getCuratorId());
        if(permissionAdministrator && permissionCurator) {
            int row = libraryDao.update(library);
            return (row == 1);
        }
        else {
            return false;
        }
    }
}
