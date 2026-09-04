scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajemissary_vfx.export_version dummy
scoreboard objectives add aj.ajemissary_vfx.rig_loaded dummy
scoreboard objectives add aj.ajemissary_vfx.animation.celestial_smite.local_anim_time dummy
scoreboard objectives add aj.ajemissary_vfx.animation.explosion.local_anim_time dummy
scoreboard objectives add aj.ajemissary_vfx.animation.celestial_smite.loop_mode dummy
scoreboard objectives add aj.ajemissary_vfx.animation.explosion.loop_mode dummy
scoreboard players set $aj.ajemissary_vfx.animation.celestial_smite aj.id 0
scoreboard players set $aj.ajemissary_vfx.animation.explosion aj.id 1
scoreboard players set $aj.ajemissary_vfx.variant.default aj.id 0
scoreboard players set $aj.ajemissary_vfx.variant.celestial_smite aj.id 1
scoreboard players set $aj.ajemissary_vfx.variant.explosion aj.id 2
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajemissary_vfx.export_version aj.i -889744400
scoreboard players reset * aj.ajemissary_vfx.rig_loaded
execute as @e[type=minecraft:item_display,tag=aj.ajemissary_vfx.root] run function animated_java:ajemissary_vfx/zzzzzzzz/on_load