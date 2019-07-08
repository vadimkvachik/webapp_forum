package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.exception.DaoException;
import com.vpd.courseproject.forum.exception.ServiceException;
import com.vpd.courseproject.forum.persistence.dao.SectionBlockDao;
import com.vpd.courseproject.forum.persistence.dao.api.ISectionBlockDao;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import com.vpd.courseproject.forum.service.api.ISectionBlockService;
import com.vpd.courseproject.forum.utils.FromISO88591toUTF8Encoder;
import com.vpd.courseproject.forum.utils.api.IEncoder;
import org.apache.log4j.Logger;

import java.util.List;

public class SectionBlockService implements ISectionBlockService {
    private static final SectionBlockService instance = new SectionBlockService();
    private static final Logger logger = Logger.getLogger(SectionBlockService.class);
    private ISectionBlockDao sectionBlockDao = SectionBlockDao.getInstance();
    private IEncoder encoder = FromISO88591toUTF8Encoder.getInstance();

    private SectionBlockService() {
    }

    public static SectionBlockService getInstance() {
        return instance;
    }

    public String addSectionBlockAndReturnPath(String login, String name) throws ServiceException {
        try {
            SectionBlock sectionBlock = new SectionBlock(encoder.encode(name));
            sectionBlockDao.addSectionBlock(sectionBlock);
            logger.info("User '" + login + "' added the section block '" + sectionBlock.getName() + "'");
            return "/#sectionBlock" + sectionBlock.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<SectionBlock> getAllSectionBlocks() throws ServiceException {
        try {
            return sectionBlockDao.getAllSectionBlocks();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public String renameSectionBlockAndReturnPath(String login, String id, String newName) throws ServiceException {
        try {
            SectionBlock sectionBlock = sectionBlockDao.getSectionBlockById(Long.valueOf(id));
            String oldName = sectionBlock.getName();
            sectionBlock.setName(encoder.encode(newName));
            sectionBlockDao.updateSectionBlock(sectionBlock);
            logger.info("User '" + login + "' renamed the section block '" + oldName + "' to '" + sectionBlock.getName() + "'");
            return "/#sectionBlock" + sectionBlock.getId();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void deleteSectionBlock(String login, String id) throws ServiceException {
        try {
            SectionBlock sectionBlock = sectionBlockDao.getSectionBlockById(Long.valueOf(id));
            sectionBlockDao.deleteSectionBlock(sectionBlock);
            logger.info("User '" + login + "' deleted the section block '" + sectionBlock.getName() + "'");
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
