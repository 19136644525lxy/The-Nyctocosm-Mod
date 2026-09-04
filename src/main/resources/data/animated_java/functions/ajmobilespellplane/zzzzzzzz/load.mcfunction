scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajmobilespellplane.export_version dummy
scoreboard objectives add aj.ajmobilespellplane.rig_loaded dummy
scoreboard objectives add aj.ajmobilespellplane.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajmobilespellplane.animation.cast.local_anim_time dummy
scoreboard objectives add aj.ajmobilespellplane.animation.fizzle.local_anim_time dummy
scoreboard objectives add aj.ajmobilespellplane.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajmobilespellplane.animation.cast.loop_mode dummy
scoreboard objectives add aj.ajmobilespellplane.animation.fizzle.loop_mode dummy
scoreboard players set $aj.ajmobilespellplane.animation.idle aj.id 0
scoreboard players set $aj.ajmobilespellplane.animation.cast aj.id 1
scoreboard players set $aj.ajmobilespellplane.animation.fizzle aj.id 2
scoreboard players set $aj.ajmobilespellplane.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajmobilespellplane.export_version aj.i 241398781
scoreboard players reset * aj.ajmobilespellplane.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajmobilespellplane.root] run function animated_java:ajmobilespellplane/zzzzzzzz/on_load