scoreboard players set @s aj.death_walker.rig_loaded 1
execute unless score @s aj.export_version = .aj.export_version aj.i at @s run function zzz_death_walker_internal:upgrade_rig
function #death_walker:on_load