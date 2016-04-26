package com.github.xb10.scorecard;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.github.xb10.scorecard.model.Club;
import com.github.xb10.scorecard.model.Course;

import java.util.HashMap;
import java.util.List;

public class CourseChooserListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Club> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Course>> listDataContent;

    public CourseChooserListAdapter(Context context, List<Club> listDataHeader, HashMap<String, List<Course>> listDataContent) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataContent = listDataContent;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Course chosenCourse = (Course) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_course_chooser, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lbl_list_item);
        txtListChild.setText(chosenCourse.getName());

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataContent.get( listDataHeader.get(groupPosition).getName() ).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public int getChildrenCount(int groupPosition) {

        return listDataContent.get( listDataHeader.get(groupPosition).getName() ).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Club selectedClub = (Club) getGroup(groupPosition);

        String headerTitle = selectedClub.getName();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_group_course_chooser, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lbl_list_header_course_chooser);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}