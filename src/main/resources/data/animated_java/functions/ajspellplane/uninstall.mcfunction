scoreboard objectives remove aj.i
scoreboard objectives remove aj.id
scoreboard objectives remove aj.tween_time
scoreboard objectives remove aj.anim_time
scoreboard objectives remove aj.life_time
scoreboard objectives remove aj.ajspellplane.export_version
scoreboard objectives remove aj.ajspellplane.rig_loaded
scoreboard objectives remove aj.ajspellplane.animation.idle.local_anim_time
scoreboard objectives remove aj.ajspellplane.animation.cast.local_anim_time
scoreboard objectives remove aj.ajspellplane.animation.fizzle.local_anim_time
scoreboard objectives remove aj.ajspellplane.animation.idle.loop_mode
scoreboard objectives remove aj.ajspellplane.animation.cast.loop_mode
scoreboard objectives remove aj.ajspellplane.animation.fizzle.loop_mode
tellraw @a ["",{"text":"["},{"text":"Animated Java","color":"aqua"},{"text":"] "},[{"text":"INFO ℹ","color":"green"},{"text":" > ","color":"gray"},{"text":"The ajspellplane Rig has been uninstalled successfully.\n"},{"text":"Please remove the Rig's functions from the datapack before reloading.","color":"gray"}]]