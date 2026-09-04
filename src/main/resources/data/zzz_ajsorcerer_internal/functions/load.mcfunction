scoreboard objectives add aj.i dummy
scoreboard objectives add aj.id dummy
scoreboard objectives add aj.tween_time dummy
scoreboard objectives add aj.anim_time dummy
scoreboard objectives add aj.life_time dummy
scoreboard objectives add aj.ajsorcerer.export_version dummy
scoreboard objectives add aj.ajsorcerer.rig_loaded dummy
scoreboard objectives add aj.ajsorcerer.animation.test.local_anim_time dummy
scoreboard objectives add aj.ajsorcerer.animation.initial_spawn.local_anim_time dummy
scoreboard objectives add aj.ajsorcerer.animation.test.loop_mode dummy
scoreboard objectives add aj.ajsorcerer.animation.initial_spawn.loop_mode dummy
scoreboard players set $aj.ajsorcerer.animation.test aj.id 0
scoreboard players set $aj.ajsorcerer.animation.initial_spawn aj.id 1
scoreboard players set $aj.ajsorcerer.variant.defaultog aj.id 0
scoreboard players set $aj.ajsorcerer.variant.staffless aj.id 1
scoreboard players set $aj.ajsorcerer.variant.slash aj.id 2
scoreboard players set $aj.ajsorcerer.variant.staff aj.id 3
scoreboard players set $aj.ajsorcerer.variant.slashstaffless aj.id 4
scoreboard players add .aj.last_id aj.id 0
scoreboard players set $aj.default_interpolation_duration aj.i 1
scoreboard players set $aj.loop_mode.loop aj.i 0
scoreboard players set $aj.loop_mode.once aj.i 1
scoreboard players set $aj.loop_mode.hold aj.i 2
scoreboard players set aj.ajsorcerer.export_version aj.i -500027743
scoreboard players reset * aj.ajsorcerer.rig_loaded
execute as @e[type=#ajsorcerer:aj_root,tag=aj.ajsorcerer.root] run function zzz_ajsorcerer_internal:on_load