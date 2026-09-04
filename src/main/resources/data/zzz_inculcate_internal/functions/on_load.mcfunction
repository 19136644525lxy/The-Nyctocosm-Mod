scoreboard players set @s aj.inculcate.rig_loaded 1
execute unless score @s aj.export_version = .aj.export_version aj.i at @s run function zzz_inculcate_internal:upgrade_rig
function #inculcate:on_load