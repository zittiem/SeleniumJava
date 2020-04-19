package datatype.LGmail;

public class Enums {
	public enum MailTreeItem {
		INBOX("Inbox"), DRAFTS("Drafts"), SENT_ITEMS("Sent Items"), DELETED_ITEMS("Deleted Items");

		private String value;

		private MailTreeItem(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;

		}
	}

	public enum MessageToolbarItem {
		NEW("New", "newmsgc"), DELETE("Delete", "delete"), MOVE("Move", "move"), FILTER("Filter", "fltrc"),
		VIEW("View", "rps");

		private String value;
		private String id;

		private MessageToolbarItem(String value, String id) {
			this.value = value;
			this.id = id;
		}

		public String getValue() {
			return this.value;
		}

		public String getID() {
			return this.id;
		}

	}

	public enum ComposeActions {
		SEND("send"), SAVE("save"), ATTACH_FILE("attachfile"), INSERT_IMAGE("insertimage"), ADDRESS_BOOK("addressbook"),
		CHECK_NAMES("checknames");

		private String id;

		ComposeActions(String id) {
			this.id = id;
		}

		public String getID() {
			return this.id;
		}
	}
}
