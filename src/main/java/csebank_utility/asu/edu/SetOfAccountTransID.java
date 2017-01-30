package csebank_utility.asu.edu;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public class SetOfAccountTransID implements Serializable{
	Set<String> idSet;

	public Set<String> getIdSet() {
		return idSet;
	}

	public void setIdSet(Set<String> idSet) {
		this.idSet = idSet;
	}

}