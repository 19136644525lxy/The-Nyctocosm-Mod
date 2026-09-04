scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajnullpoint.export_version dummy
scoreboard objectives add aj.ajnullpoint.rig_loaded dummy
scoreboard objectives add aj.ajnullpoint.animation.idle.local_anim_time dummy
scoreboard objectives add aj.ajnullpoint.animation.emit.local_anim_time dummy
scoreboard objectives add aj.ajnullpoint.animation.recall.local_anim_time dummy
scoreboard objectives add aj.ajnullpoint.animation.hurt.local_anim_time dummy
scoreboard objectives add aj.ajnullpoint.animation.idle.loop_mode dummy
scoreboard objectives add aj.ajnullpoint.animation.emit.loop_mode dummy
scoreboard objectives add aj.ajnullpoint.animation.recall.loop_mode dummy
scoreboard objectives add aj.ajnullpoint.animation.hurt.loop_mode dummy
scoreboard players set $aj.ajnullpoint.animation.idle aj.id 0
scoreboard players set $aj.ajnullpoint.animation.emit aj.id 1
scoreboard players set $aj.ajnullpoint.animation.recall aj.id 2
scoreboard players set $aj.ajnullpoint.animation.hurt aj.id 3
scoreboard players set $aj.ajnullpoint.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajnullpoint.export_version aj.i -499573089
scoreboard players reset * aj.ajnullpoint.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajnullpoint.root] run function animated_java:ajnullpoint/zzzzzzzz/on_load