package com.reddit.luisordoniez.reddit.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luisordoniez on 14/12/16.
 * <p/>
 * Esta clase recoje todos los campos del JSON con key 'data'
 * implementa de Serializable para poder pasarse entre activities
 */

public class Data implements Serializable {

    /**
     * Atributos
     */

    private String banner_img;
    private Boolean user_sr_theme_enabled;
    private String submit_text_html;
    private Object user_is_banned;
    private Boolean wiki_enabled;
    private Boolean show_media;
    private String id;
    private String submit_text;
    private String display_name;
    private String header_img;
    private String description_html;
    private String title;
    private Boolean collapse_deleted_comments;
    private Boolean over18;
    private String public_description_html;
    private List<Integer> icon_size = null;
    private String suggested_comment_sort;
    private String icon_img;
    private String header_title;
    private String description;
    private Object user_is_muted;
    private String submit_link_label;
    private Object accounts_active;
    private Boolean public_traffic;
    private List<Integer> header_size = null;
    private Integer subscribers;
    private String submit_text_label;
    private String lang;
    private Object user_is_moderator;
    private String key_color;
    private String name;
    private Integer created;
    private String url;
    private Boolean quarantine;
    private Boolean hide_ads;
    private Integer created_utc;
    private List<Integer> banner_size = null;
    private Object user_is_contributor;
    private String public_description;
    private Boolean show_media_preview;
    private Integer comment_score_hide_mins;
    private String subreddit_type;
    private String submission_type;
    private Object user_is_subscriber;


    /**
     * Metodos Get y Set
     */

    public String getBanner_img() {
        return banner_img;
    }
    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public Boolean getUser_sr_theme_enabled() {
        return user_sr_theme_enabled;
    }
    public void setUser_sr_theme_enabled(Boolean user_sr_theme_enabled) {
        this.user_sr_theme_enabled = user_sr_theme_enabled;
    }

    public String getSubmit_text_html() {
        return submit_text_html;
    }
    public void setSubmit_text_html(String submit_text_html) {
        this.submit_text_html = submit_text_html;
    }

    public Object getUser_is_banned() {
        return user_is_banned;
    }
    public void setUser_is_banned(Object user_is_banned) {
        this.user_is_banned = user_is_banned;
    }

    public Boolean getWiki_enabled() {
        return wiki_enabled;
    }
    public void setWiki_enabled(Boolean wiki_enabled) {
        this.wiki_enabled = wiki_enabled;
    }

    public Boolean getShow_media() {
        return show_media;
    }
    public void setShow_media(Boolean show_media) {
        this.show_media = show_media;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getSubmit_text() {
        return submit_text;
    }
    public void setSubmit_text(String submit_text) {
        this.submit_text = submit_text;
    }

    public String getDisplay_name() {
        return display_name;
    }
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getHeader_img() {
        return header_img;
    }
    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public String getDescription_html() {
        return description_html;
    }
    public void setDescription_html(String description_html) {
        this.description_html = description_html;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCollapse_deleted_comments() {
        return collapse_deleted_comments;
    }
    public void setCollapse_deleted_comments(Boolean collapse_deleted_comments) {
        this.collapse_deleted_comments = collapse_deleted_comments;
    }

    public Boolean getOver18() {
        return over18;
    }
    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public String getPublic_description_html() {
        return public_description_html;
    }
    public void setPublic_description_html(String public_description_html) {
        this.public_description_html = public_description_html;
    }

    public List<Integer> getIcon_size() {
        return icon_size;
    }
    public void setIcon_size(List<Integer> icon_size) {
        this.icon_size = icon_size;
    }

    public String getSuggested_comment_sort() {
        return suggested_comment_sort;
    }
    public void setSuggested_comment_sort(String suggested_comment_sort) {
        this.suggested_comment_sort = suggested_comment_sort;
    }

    public String getIcon_img() {
        return icon_img;
    }
    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }

    public String getHeader_title() {
        return header_title;
    }
    public void setHeader_title(String header_title) {
        this.header_title = header_title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Object getUser_is_muted() {
        return user_is_muted;
    }
    public void setUser_is_muted(Object user_is_muted) {
        this.user_is_muted = user_is_muted;
    }

    public String getSubmit_link_label() {
        return submit_link_label;
    }
    public void setSubmit_link_label(String submit_link_label) {
        this.submit_link_label = submit_link_label;
    }

    public Object getAccounts_active() {
        return accounts_active;
    }
    public void setAccounts_active(Object accounts_active) {
        this.accounts_active = accounts_active;
    }

    public Boolean getPublic_traffic() {
        return public_traffic;
    }
    public void setPublic_traffic(Boolean public_traffic) {
        this.public_traffic = public_traffic;
    }

    public List<Integer> getHeader_size() {
        return header_size;
    }
    public void setHeader_size(List<Integer> header_size) {
        this.header_size = header_size;
    }

    public Integer getSubscribers() {
        return subscribers;
    }
    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public String getSubmit_text_label() {
        return submit_text_label;
    }
    public void setSubmit_text_label(String submit_text_label) {
        this.submit_text_label = submit_text_label;
    }

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }

    public Object getUser_is_moderator() {
        return user_is_moderator;
    }
    public void setUser_is_moderator(Object user_is_moderator) {
        this.user_is_moderator = user_is_moderator;
    }

    public String getKey_color() {
        return key_color;
    }
    public void setKey_color(String key_color) {
        this.key_color = key_color;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreated() {
        return created;
    }
    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getQuarantine() {
        return quarantine;
    }
    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    public Boolean getHide_ads() {
        return hide_ads;
    }
    public void setHide_ads(Boolean hide_ads) {
        this.hide_ads = hide_ads;
    }

    public Integer getCreated_utc() {
        return created_utc;
    }
    public void setCreated_utc(Integer created_utc) {
        this.created_utc = created_utc;
    }

    public List<Integer> getBanner_size() {
        return banner_size;
    }
    public void setBanner_size(List<Integer> banner_size) {
        this.banner_size = banner_size;
    }

    public Object getUser_is_contributor() {
        return user_is_contributor;
    }
    public void setUser_is_contributor(Object user_is_contributor) {
        this.user_is_contributor = user_is_contributor;
    }

    public String getPublic_description() {
        return public_description;
    }
    public void setPublic_description(String public_description) {
        this.public_description = public_description;
    }

    public Boolean getShow_media_preview() {
        return show_media_preview;
    }
    public void setShow_media_preview(Boolean show_media_preview) {
        this.show_media_preview = show_media_preview;
    }

    public Integer getComment_score_hide_mins() {
        return comment_score_hide_mins;
    }
    public void setComment_score_hide_mins(Integer comment_score_hide_mins) {
        this.comment_score_hide_mins = comment_score_hide_mins;
    }

    public String getSubreddit_type() {
        return subreddit_type;
    }
    public void setSubreddit_type(String subreddit_type) {
        this.subreddit_type = subreddit_type;
    }

    public String getSubmission_type() {
        return submission_type;
    }
    public void setSubmission_type(String submission_type) {
        this.submission_type = submission_type;
    }

    public Object getUser_is_subscriber() {
        return user_is_subscriber;
    }
    public void setUser_is_subscriber(Object user_is_subscriber) {
        this.user_is_subscriber = user_is_subscriber;
    }
}