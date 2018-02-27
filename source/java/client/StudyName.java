/*---------------------------------------------------------------
*  Copyright 2005 by the Radiological Society of North America
*
*  This source software is released under the terms of the
*  RSNA Public License (http://mirc.rsna.org/rsnapubliclicense)
*----------------------------------------------------------------*/

package client;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

public class StudyName extends JButton {

	int numberOfSeries = 0;
	String name = "";
	String classification = "";
	FileName fn = null;
	LinkedList<String> modalities = null;
	String frameInfo = "";
	String studyDescription = null;

	public StudyName(FileName fileName) {
		super();
		fn = fileName;
		studyDescription = fn.getStudyDescription();
		updateName();
		setFont( new Font( "Monospaced", Font.BOLD, 17 ) );
		setForeground( Color.blue );
		setBorder(BorderFactory.createEmptyBorder());
		setBorderPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setFocusPainted(false);
		modalities = new LinkedList<String>();
	}

	public void changeDisplayedStudyDescription (String nsd) {
		studyDescription = nsd;
		updateName();
	}

	public void setNumberOfSeries(int i) {
		numberOfSeries = i;
		updateName();
	}

	public void setFrameOfRefInfo() {
		setForeground(Color.red);
		frameInfo = " !FrameOfRef!";
		updateName();
	}

	public void addSeriesModality(String modality) {
		if (!modalities.contains(modality)) {
			modalities.add(modality);
		}
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification() {
		if (modalities.contains("CT")) {
			classification = "CT";
			if (modalities.contains("PT")) {
				classification = "PET-CT";
			}
			else if (modalities.contains("RTSTRUCT")) {
				if (modalities.contains("RTPLAN")
						&& modalities.contains("RTDOSE")) {
					classification = "TreatmentPlan";
				}
				else{
					classification = "Contouring";
				}
			}
		}
		else if (modalities.contains("MR")) {
			classification = "MRI";
			if (modalities.contains("PT")) {
				classification = "PET-MRI";
			}
		}
		else {
			classification = "OTH";
			modalities.remove();
		}
		updateName();
	}

	public void updateName() {
		String desc = studyDescription + frameInfo;
		name = String.format("%-16s", "[" + classification + "]")
				+ " " +	String.format("%-31s", desc.substring(0,
					Math.min(desc.length(), 31)))
				+ " " + String.format("%-12s", "<Series=" + String.format("%03d", numberOfSeries) + ">")
				+ " " + String.format("%-10s", "  STUDY")
				+ " " + String.format("%-2s", fn.getStudyDate());

				//+ " " + fn.getFrameOfReference();
		setText(name);
	}
}
