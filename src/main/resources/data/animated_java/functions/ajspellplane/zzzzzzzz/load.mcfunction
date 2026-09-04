scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajspellplane.export_version dummy
scoreboard objectives add aj.ajspellplane.rig_loaded dummy
scoreboard objectives add aj.ajspellplane.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajspellplane.animation.cast.local_anim_time dummy
scoreboard objectives add aj.ajspellplane.animation.fizzle.local_anim_time dummy
scoreboard objectives add aj.ajspellplane.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajspellplane.animation.cast.loop_mode dummy
scoreboard objectives add aj.ajspellplane.animation.fizzle.loop_mode dummy
scoreboard players set $aj.ajspellplane.animation.idle aj.id 0
scoreboard players set $aj.ajspellplane.animation.cast aj.id 1
scoreboard players set $aj.ajspellplane.animation.fizzle aj.id 2
scoreboard players set $aj.ajspellplane.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajspellplane.export_version aj.i -160429013
scoreboard players reset * aj.ajspellplane.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajspellplane.root] run function animated_java:ajspellplane/zzzzzzzz/on_load