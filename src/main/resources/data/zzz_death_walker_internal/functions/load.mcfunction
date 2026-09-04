scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.loop_mode dummy
scoreboard objectives add aj.export_version dummy
scoreboard objectives add aj.death_walker.rig_loaded dummy
scoreboard objectives add aj.animation.walk.local_anim_time dummy
scoreboard objectives add aj.animation.slam.local_anim_time dummy
scoreboard objectives add aj.animation.idle.local_anim_time dummy
scoreboard players set $aj.death_walker.animation.walk aj.id 0
scoreboard players set $aj.death_walker.animation.slam aj.id 1
scoreboard players set $aj.death_walker.animation.idle aj.id 2
scoreboard players set $aj.death_walker.variant.default aj.id 0
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.default_interpolation_duration aj.i 1
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set .aj.export_version aj.i -315278999
scoreboard players reset * aj.death_walker.rig_loaded
execute as @e[type=#death_walker:aj_root,tag=aj.death_walker.root] run function #death_walker:on_load