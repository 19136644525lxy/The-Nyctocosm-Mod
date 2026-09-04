scoreboard players set @s aj.test.rig_loaded 1
execute unless score @s aj.export_version = .aj.export_version aj.i at @s run function zzz_test_internal:upgrade_rig
function #test:on_load