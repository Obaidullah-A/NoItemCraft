# NoItemCraft Plugin

NoItemCraft is a Minecraft server plugin that allows server administrators to block or unblock certain items from being crafted. This is useful for balancing gameplay.

## Features

- Block specific items from being crafted.
- Unblock items to allow them to be crafted again.
- Customizable messages for in-game notifications.
- Easy-to-use commands for managing blocked items.

## Installation

1. Download the NoItemCraft plugin jar file from the releases.
2. Place the jar file in your server's `plugins` directory.
3. Start or restart your server to load the plugin.

## Configuration

The plugin uses a `config.yml` file to manage blocked items and custom messages. The file is automatically created in the `plugins/NoItemCraft` directory when you first run the plugin.

### Default `config.yml`

```yaml
prohibited-items:
  - HOPPER
  - DIAMOND_SWORD

messages:
  item_blocked: "You cannot craft this item. :)"
  item_already_blocked: "Item {item} is already blocked."
  item_block_success: "Item {item} has been blocked."
  item_unblock_success: "Item {item} has been unblocked."
  item_not_blocked: "Item {item} is not blocked."
  invalid_item: "Invalid item: {item}."
  command_usage: "Usage: /noitemcraft <block|unblock> <item>"
```
## Commands

### `/noitemcraft block <item>`

Blocks the specified item from being crafted.

- **Permission**: `noitemcraft.manage`
- **Usage**: `/noitemcraft block <item>`

### `/noitemcraft unblock <item>`

Unblocks the specified item, allowing it to be crafted again.

- **Permission**: `noitemcraft.manage`
- **Usage**: `/noitemcraft unblock <item>`

## Permissions

- `noitemcraft.manage`: Allows the player to block or unblock items from being crafted. Default is set to `op`.

## Development

If you want to contribute to the development of this plugin, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Obaidullah-A/NoItemCraft.git
   ```
Open the project in your favorite IDE.
Make your changes and test them locally.
Submit a pull request with a detailed description of your changes.
## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

If you have any questions or need support, please open an issue on the [GitHub repository](https://github.com/Obaidullah-A/NoItemCraft).
