scoreboard objectives remove aj.i
scoreboard objectives remove aj.id
scoreboard objectives remove aj.tween_time
scoreboard objectives remove aj.anim_time
scoreboard objectives remove aj.life_time
scoreboard objectives remove aj.ajsorcerer.export_version
scoreboard objectives remove aj.ajsorcerer.rig_loaded
scoreboard objectives remove aj.ajsorcerer.animation.test.local_anim_time
scoreboard objectives remove aj.ajsorcerer.animation.initial_spawn.local_anim_time
scoreboard objectives remove aj.ajsorcerer.animation.test.loop_mode
scoreboard objectives remove aj.ajsorcerer.animation.initial_spawn.loop_mode
tellraw @a ["",{"text":"["},{"text":"Animated Java","color":"aqua"},{"text":"] "},[{"text":"INFO ℹ","color":"green"},{"text":" > ","color":"gray"},{"text":"The ajsorcerer Rig has been uninstalled successfully.\n"},{"text":"Please remove the Rig's functions from the datapack before reloading.","color":"gray"}]]